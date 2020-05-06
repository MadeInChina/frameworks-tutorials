package org.hanrw.rabbitmq.transaction.entity;

import java.util.Date;

import lombok.Data;

/**
 * 创建支付消息
 */
@Data
public class CreatePaymentMsg {

  private String msgId;

  private long orderId;

  private Integer paymentId;

  private Date createTime;

  private Date updateTime;

  private Integer status;

  private String errCause;

  private Integer maxRetry;

  private Integer currentRetry = 0;
}
