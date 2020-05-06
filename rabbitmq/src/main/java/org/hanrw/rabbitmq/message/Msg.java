package org.hanrw.rabbitmq.message;

import java.io.Serializable;

import lombok.Data;

/**
 * @author hanrw
 * @date 2020/5/6 3:25 PM
 */
@Data
public class Msg implements Serializable {

  private long orderId;

  private int paymentId;

  private String msgId;
}
