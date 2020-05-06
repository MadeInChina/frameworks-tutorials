package org.hanrw.rabbitmq.transaction.dao;

import java.util.List;

import org.hanrw.rabbitmq.transaction.entity.CreatePaymentMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreatePaymentMsgDao {

  @Autowired private JdbcTemplate jdbcTemplate;

  public int updateStatus(String messageId, Integer status) {
    String sql = "update create_payment_msg set status=? where msg_id=?";
    return jdbcTemplate.update(sql, status, messageId);
  }

  public int create(CreatePaymentMsg createPaymentMsg) {
    String sql =
        "insert into create_payment_msg(order_id,payment_id,create_time,update_time,status,err_cause,max_retry,current_retry) values(?,?,?,?,?,?,?,?)";
    return jdbcTemplate.update(
        sql,
        createPaymentMsg.getOrderId(),
        createPaymentMsg.getPaymentId(),
        createPaymentMsg.getCreateTime(),
        createPaymentMsg.getUpdateTime(),
        createPaymentMsg.getStatus(),
        createPaymentMsg.getErrCause(),
        createPaymentMsg.getMaxRetry(),
        createPaymentMsg.getCurrentRetry());
  }

  public List<CreatePaymentMsg> queryNeedRetryMsg() {
    // 查询在五分钟内还没有被成功消费的消息CONSUMER_SUCCESS
    String sql = "select * from create_payment_msg where status != 3";
    return jdbcTemplate.queryForList(sql, CreatePaymentMsg.class);
  }

  public List<CreatePaymentMsg> queryNeedRetryMsg(Integer status, Integer timeDiff) {
    // 查询在五分钟内还没有被成功消费的消息CONSUMER_SUCCESS
    String sql = "select * from create_payment_msg where status != 3";
    return jdbcTemplate.queryForList(sql, CreatePaymentMsg.class);
  }

  public int updateMsgRetryCount(String msgId) {
    String sql = "update create_payment_msg set balance=balance+1 where msg_id=?";
    return jdbcTemplate.update(sql, msgId);
  }
}
