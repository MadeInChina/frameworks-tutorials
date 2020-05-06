package org.hanrw.rabbitmq.transaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

  @Autowired private JdbcTemplate jdbcTemplate;

  public int create(Integer productId, double price) {
    String sql = "insert into order(product_id,price) values(?,?)";
    return jdbcTemplate.update(sql, productId, price);
  }
}
