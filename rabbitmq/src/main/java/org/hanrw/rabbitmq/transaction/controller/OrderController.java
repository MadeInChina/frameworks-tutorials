package org.hanrw.rabbitmq.transaction.controller;

import org.hanrw.rabbitmq.transaction.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired private OrderService orderService;

  @RequestMapping("/order/create")
  public String saveOrder() {
    orderService.createWithPaymentMsg(1001, 100);
    return "ok";
  }
}
