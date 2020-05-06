package org.hanrw.rabbitmq.transaction.entity;

import lombok.Data;

@Data
public class Order {

  private Integer id;

  private String productId;

  private double price;
}
