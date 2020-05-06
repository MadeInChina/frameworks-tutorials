package org.hanrw.rabbitmq.transaction.service;

import org.hanrw.rabbitmq.message.Msg;

import org.springframework.stereotype.Component;

/** @author hanrenwei */
@Component
public class PayServiceImpl implements PayService {

  @Override
  public void pay(Msg msg) {
    // 业务处理
  }
}
