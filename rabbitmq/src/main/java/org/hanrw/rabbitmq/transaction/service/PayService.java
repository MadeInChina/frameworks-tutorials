package org.hanrw.rabbitmq.transaction.service;

import org.hanrw.rabbitmq.message.Msg;

public interface PayService {

  void pay(Msg msg);
}
