package org.hanrw.rabbitmq.message;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hanrw
 * @date 2020/5/6 2:39 PM
 */
@Component
@Slf4j
public class MsgSender implements InitializingBean {

  @Autowired private RabbitTemplate rabbitTemplate;
  @Autowired private MsgConfirmCallback msgConfirmCallback;

  @Override
  public void afterPropertiesSet() throws Exception {
    rabbitTemplate.setConfirmCallback(msgConfirmCallback);
    Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
  }

  public void send(Msg msg) {
    log.info("Sending msg ID:{}", msg.getMsgId());

    CorrelationData correlationData = new CorrelationData(msg.getMsgId());

    rabbitTemplate.convertAndSend(
        RabbitMQConsts.ORDER_TO_PAYMENT_EXCHANGE_NAME,
        RabbitMQConsts.ORDER_TO_PAYMENT_ROUTING_KEY,
        msg,
        correlationData);
  }
}
