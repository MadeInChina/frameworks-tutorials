package org.hanrw.rabbitmq.transaction.service;

import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;

public interface OrderService {

  /**
   * 创建Order和Payment消息
   * @param productId
   * @param price
   * @param createPaymentMsg
   */
  void create(Integer productId, Integer price, CreatePaymentMsg createPaymentMsg);

  /**
   * 创建新Order和Payment异步消息
   * @param productId
   * @param price
   */
  void createWithPaymentMsg(Integer productId, Integer price);

}
