CREATE TABLE `account_info`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `account_id` varchar(100)   DEFAULT NULL,
    `balance`    double(255, 0) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8;


CREATE TABLE `product_info`
(
    `product_id`   int(11) NOT NULL AUTO_INCREMENT,
    `product_name` varchar(255) DEFAULT NULL,
    `store`        int(11)      DEFAULT NULL,
    PRIMARY KEY (`product_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;



CREATE TABLE `create_payment_msg`
(
    `msg_id`        VARCHAR(50) NOT NULL,
    `order_id`      int(255)     DEFAULT NULL,
    `payment_id`    int(255)     DEFAULT NULL,
    `create_time`   DATE         DEFAULT NULL,
    `update_time`   DATE         DEFAULT NULL,
    `status`        int(1)       DEFAULT NULL,
    `err_cause`     VARCHAR(100) DEFAULT NULL,
    `max_retry`     int(2)       DEFAULT NULL,
    `current_retry` int(2)       DEFAULT NULL,
    PRIMARY KEY (`msg_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;