package org.hanrw.rabbitmq.message;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;
import org.hanrw.rabbitmq.transaction.service.CreatePaymentMsgService;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbitmq消息确认回调方法
 *
 * @author hanrw
 * @date 2020/5/6 2:42 PM
 */
@Component
@Slf4j
public class MsgConfirmCallback implements RabbitTemplate.ConfirmCallback {

  @Autowired private CreatePaymentMsgService createPaymentMsgService;

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    String msgId = correlationData.getId();

    if (ack) {
      log.info("Msg Id:{}被broker成功签收", msgId);
      updateMsgStatusWithAck(msgId);
    } else {
      log.warn("消息Id:{}对应的消息被broker签收失败:{}", msgId, cause);
      updateMsgStatusWithNack(msgId, cause);
    }
  }

  private void updateMsgStatusWithAck(String msgId) {
    CreatePaymentMsg messageContent = builderUpdateContent(msgId);
    messageContent.setStatus(MsgStatusEnum.SENDING_SUCCESS.getCode());
    createPaymentMsgService.updateMsgStatus(messageContent);
  }

  private void updateMsgStatusWithNack(String msgId, String cause) {

    CreatePaymentMsg messageContent = builderUpdateContent(msgId);

    messageContent.setStatus(MsgStatusEnum.SENDING_FAIL.getCode());
    messageContent.setErrCause(cause);
    createPaymentMsgService.updateMsgStatus(messageContent);
  }

  private CreatePaymentMsg builderUpdateContent(String msgId) {
    CreatePaymentMsg messageContent = new CreatePaymentMsg();
    messageContent.setMsgId(msgId);
    messageContent.setUpdateTime(new Date());
    return messageContent;
  }
}
