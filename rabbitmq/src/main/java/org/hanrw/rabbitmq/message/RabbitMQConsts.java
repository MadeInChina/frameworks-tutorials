package org.hanrw.rabbitmq.message;

/**
 * @author hanrw
 * @date 2020/5/6 3:29 PM
 */
public class RabbitMQConsts {
  /** 交换机名称 */
  public static final String ORDER_TO_PAYMENT_EXCHANGE_NAME = "order-to-payment.exchange";

  /** 队列名称 */
  public static final String ORDER_TO_PAYMENT_QUEUE_NAME = "order-to-payment.queue";

  /** 路由key */
  public static final String ORDER_TO_PAYMENT_ROUTING_KEY = "order-to-payment.key";

  /** 消息重发的最大次数 */
  public static final Integer RETRY_COUNT = 5;

  public static final Integer TIME_DIFF = 30;
}
