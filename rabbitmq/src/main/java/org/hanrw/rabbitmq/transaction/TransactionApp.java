package org.hanrw.rabbitmq.transaction;

import org.hanrw.rabbitmq.transaction.config.TransactionConfig;
import org.hanrw.rabbitmq.transaction.service.PayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionApp {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(TransactionConfig.class);
    PayService payService = context.getBean(PayService.class);
    payService.pay("1", 5);
  }
}
