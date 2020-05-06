package org.hanrw.rabbitmq.transaction.service;

import java.util.List;

import org.hanrw.rabbitmq.transaction.dao.CreatePaymentMsgDao;
import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 更新创建支付消息状态
 * @author hanrw
 * @date 2020/5/6 2:49 PM
 */
public class CreatePaymentMsgServiceImpl implements CreatePaymentMsgService {
  @Autowired CreatePaymentMsgDao createPaymentMsgDao;

  @Override
  public void updateMsgStatus(CreatePaymentMsg messageContent) {
    createPaymentMsgDao.updateStatus(messageContent.getMsgId(), messageContent.getStatus());
  }

  @Override
  public List<CreatePaymentMsg> queryNeedRetryMsg() {
    return createPaymentMsgDao.queryNeedRetryMsg();
  }

  @Override
  public List<CreatePaymentMsg> queryNeedRetryMsg(Integer status, Integer timeDiff) {
    return createPaymentMsgDao.queryNeedRetryMsg(status,timeDiff);
  }

  @Override
  public void updateMsgRetryCount(String msgId) {
    createPaymentMsgDao.updateMsgRetryCount(msgId);
  }
}
