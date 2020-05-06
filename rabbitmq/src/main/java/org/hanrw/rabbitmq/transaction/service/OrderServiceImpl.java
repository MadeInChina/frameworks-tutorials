package org.hanrw.rabbitmq.transaction.service;

import java.util.Date;
import java.util.UUID;

import org.hanrw.rabbitmq.message.Msg;
import org.hanrw.rabbitmq.message.MsgSender;
import org.hanrw.rabbitmq.message.MsgStatusEnum;
import org.hanrw.rabbitmq.transaction.dao.CreatePaymentMsgDao;
import org.hanrw.rabbitmq.transaction.dao.OrderDao;
import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 使用rabbitmq作为异步事务处理
 *
 * @author hanrw
 * @date 2020/5/6 2:35 PM
 */
@Service
public class OrderServiceImpl implements OrderService {
  @Autowired OrderDao orderDao;
  @Autowired CreatePaymentMsgDao createPaymentMsgDao;
  @Autowired MsgSender msgSender;

  @Transactional
  @Override
  public void create(Integer productId, Integer price, CreatePaymentMsg createPaymentMsg) {
    // 创建Order
    orderDao.create(productId, price);

    // 创建支付消息
    createPaymentMsgDao.create(createPaymentMsg);
  }

  @Override
  public void createWithPaymentMsg(Integer productId, Integer price) {
    CreatePaymentMsg createPaymentMsg = buildPaymentMsg(productId, price);
    create(productId, price, createPaymentMsg);
    // 发送消息
    Msg msg = new Msg();
    msg.setMsgId(createPaymentMsg.getMsgId());
    msg.setOrderId(createPaymentMsg.getOrderId());
    msg.setPaymentId(createPaymentMsg.getPaymentId());
    msgSender.send(msg);
  }

  private CreatePaymentMsg buildPaymentMsg(Integer productId, Integer price) {
    CreatePaymentMsg createPaymentMsg = new CreatePaymentMsg();
    createPaymentMsg.setStatus(MsgStatusEnum.SENDING.getCode());
    createPaymentMsg.setCreateTime(new Date());
    createPaymentMsg.setUpdateTime(new Date());
    createPaymentMsg.setMsgId(UUID.randomUUID().toString());
    createPaymentMsg.setMaxRetry(15);
    return createPaymentMsg;
  }
}
