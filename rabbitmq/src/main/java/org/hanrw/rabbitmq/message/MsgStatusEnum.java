package org.hanrw.rabbitmq.message;

import lombok.Getter;
/**
 * @author hanrw
 * @date 2020/5/6 2:39 PM
 */
@Getter
public enum MsgStatusEnum {
  SENDING(0, "Sending"),

  SENDING_SUCCESS(1, "Send_Success"),

  SENDING_FAIL(2, "Send_Fail"),

  CONSUMER_SUCCESS(3, "Consume_Success"),

  CONSUMER_FAIL(4, "Consume_Fail");

  private Integer code;

  private String msgStatus;

  MsgStatusEnum(Integer code, String msgStatus) {
    this.code = code;
    this.msgStatus = msgStatus;
  }
}
