package org.hanrw.rabbitmq.consumer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.hanrw.rabbitmq.message.Msg;
import org.hanrw.rabbitmq.message.MsgStatusEnum;
import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;
import org.hanrw.rabbitmq.transaction.service.CreatePaymentMsgService;
import org.hanrw.rabbitmq.transaction.service.PayService;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreatePaymentMsgConsumer {

  /** 队列名称 */
  public static final String ORDER_TO_PAYMENT_QUEUE_NAME = "order-to-payment.queue";

  public static final String LOCK_KEY = "LOCK_KEY";

  @Autowired private PayService payService;

  @Autowired private CreatePaymentMsgService createPaymentMsgService;

  @Autowired private RedisTemplate redisTemplate;

  @RabbitListener(queues = {ORDER_TO_PAYMENT_QUEUE_NAME})
  @RabbitHandler
  public void consumerMsgWithLock(Message message, Channel channel) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    Msg msg = objectMapper.readValue(message.getBody(), Msg.class);
    Long deliveryTag = message.getMessageProperties().getDeliveryTag();

    if (redisTemplate.opsForValue().setIfAbsent(LOCK_KEY + msg.getMsgId(), msg.getMsgId())) {
      log.info("消费消息:{}", msg);
      try {
        // paymentService处理消息
        payService.pay(msg);
        // 消息签收
//        System.out.println(1 / 0);
        channel.basicAck(deliveryTag, false);
      } catch (Exception e) {
        /** 更新数据库异常说明业务没有操作成功需要删除分布式锁 */
        log.info("数据业务异常:{},即将删除分布式锁", e.getCause());
        // 删除分布式锁
        redisTemplate.delete(LOCK_KEY);

        // 更新消息表状态
        CreatePaymentMsg messageContent = new CreatePaymentMsg();
        messageContent.setStatus(MsgStatusEnum.CONSUMER_FAIL.getCode());
        messageContent.setUpdateTime(new Date());
        messageContent.setErrCause(e.getMessage());
        messageContent.setMsgId(msg.getMsgId());
        createPaymentMsgService.updateMsgStatus(messageContent);
        channel.basicReject(deliveryTag, false);
      }

    } else {
      log.warn("请不要重复消费消息{}", msg);
      channel.basicReject(deliveryTag, false);
    }
  }
}
