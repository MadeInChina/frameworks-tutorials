package org.hanrw.rabbitmq.task;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hanrw.rabbitmq.message.Msg;
import org.hanrw.rabbitmq.message.MsgSender;
import org.hanrw.rabbitmq.message.MsgStatusEnum;
import org.hanrw.rabbitmq.message.RabbitMQConsts;
import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;
import org.hanrw.rabbitmq.transaction.service.CreatePaymentMsgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 后台重试任务 */
@Component
@Slf4j
public class RetryMsgTask {

  @Autowired private MsgSender msgSender;

  @Autowired private CreatePaymentMsgService createPaymentMsgService;

  /** 延时5s启动 周期10S一次 */
  @Scheduled(initialDelay = 10000, fixedDelay = 10000)
  public void retrySend() {
    System.out.println("-----------------------------");
    // 查询五分钟消息状态还没有完结的消息
    List<CreatePaymentMsg> messageContentList =
        createPaymentMsgService.queryNeedRetryMsg(
            MsgStatusEnum.CONSUMER_SUCCESS.getCode(), RabbitMQConsts.TIME_DIFF);

    for (CreatePaymentMsg messageContent : messageContentList) {

      if (messageContent.getMaxRetry() > messageContent.getCurrentRetry()) {
        Msg msg = new Msg();
        msg.setMsgId(messageContent.getMsgId());
        msg.setPaymentId(messageContent.getPaymentId());
        msg.setOrderId(messageContent.getOrderId());
        // 更新消息重试次数
        createPaymentMsgService.updateMsgRetryCount(msg.getMsgId());
        msgSender.send(msg);
      } else {
        log.warn("msg:{} exceed max retry count", messageContent);
      }
    }
  }
}
