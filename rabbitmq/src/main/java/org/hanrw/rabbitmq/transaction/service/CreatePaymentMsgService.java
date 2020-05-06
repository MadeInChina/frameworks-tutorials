package org.hanrw.rabbitmq.transaction.service;

import java.util.List;

import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;

public interface CreatePaymentMsgService {

  /**
   * 更新消息状态
   *
   * @param messageContent
   */
  void updateMsgStatus(CreatePaymentMsg messageContent);

  List<CreatePaymentMsg> queryNeedRetryMsg();

  List<CreatePaymentMsg> queryNeedRetryMsg(Integer code, Integer timeDiff);

  void updateMsgRetryCount(String msgId);

}
