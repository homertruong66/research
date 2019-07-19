-- /**
--  * homertruong
--  */

-- Version 1.2.x: Commission Management, Notification Management, Bill Management, System Improvement

-- update Commission
ALTER TABLE commissions ADD COLUMN earning FLOAT NOT NULL AFTER affiliate_id;
ALTER TABLE commissions MODIFY value FLOAT NOT NULL AFTER type;


-- update Person
ALTER TABLE persons ADD COLUMN inheritor VARCHAR (255) NULL AFTER is_phone_verified;
ALTER TABLE persons ADD COLUMN nickname VARCHAR (255) NULL AFTER inheritor;
ALTER TABLE persons ADD COLUMN referrer VARCHAR (255) NULL AFTER nickname;
UPDATE persons SET discriminator = 'Admin' WHERE discriminator = 'ADMIN';
UPDATE persons SET discriminator = 'Affiliate' WHERE discriminator = 'AFFILIATE';
UPDATE persons SET discriminator = 'Channel' WHERE discriminator = 'CHANNEL';
UPDATE persons SET discriminator = 'SubsAdmin' WHERE discriminator = 'SUBS_ADMIN';


-- create PriorityGroup
DROP TABLE IF EXISTS `priority_groups`;
CREATE TABLE `priority_groups` (
	`id` VARCHAR (255) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime DEFAULT NULL,
	`commission_percent` FLOAT NOT NULL,
	`description` TEXT NOT NULL,
	`subs_custom_config_id` VARCHAR (255) NOT NULL,
	`end_date` datetime NOT NULL,
	`group_name` VARCHAR (255) NOT NULL,
	`start_date` datetime NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


-- update Payment
ALTER TABLE payments ADD COLUMN reason TEXT NULL AFTER subscriber_id;


-- update DiscountCode
DELETE FROM discount_codes;
ALTER TABLE discount_codes ADD COLUMN subscriber_id VARCHAR (255) NOT NULL AFTER start_date;


-- update DiscountCodeApplied
DELETE FROM discount_codes_applied;
ALTER TABLE discount_codes_applied ADD COLUMN subscriber_id VARCHAR (255) NOT NULL AFTER start_date;


-- create Category
DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME,

  name          VARCHAR(255) NOT NULL,
  subs_admin_id VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update Post
ALTER TABLE posts ADD COLUMN category_id VARCHAR (255) AFTER updated_at;


-- update Customer
ALTER TABLE customers ADD COLUMN subscriber_id VARCHAR (255) AFTER phone;

UPDATE customers, persons, orders
SET customers.subscriber_id = persons.subscriber_id
WHERE customers.id = orders.customer_id
  AND orders.channel_id = persons.id;

ALTER TABLE customers MODIFY subscriber_id VARCHAR(255) NOT NULL;
ALTER TABLE customers ADD COLUMN first_seller_id VARCHAR(255) AFTER email;


-- update Payment
ALTER TABLE payments ADD COLUMN created_by VARCHAR (255) AFTER updated_at;
ALTER TABLE payments ADD COLUMN updated_by VARCHAR (255) AFTER created_by;
ALTER TABLE payments MODIFY value DOUBLE NOT NULL;


-- update Agent
ALTER TABLE agents ADD COLUMN inheritor VARCHAR (255) NULL AFTER affiliate_id;
ALTER TABLE agents ADD COLUMN referrer VARCHAR (255) NULL AFTER inheritor;
ALTER TABLE agents ADD COLUMN created_by VARCHAR(255) NOT NULL AFTER created_at;
ALTER TABLE agents ADD COLUMN earning FLOAT NOT NULL DEFAULT 0 AFTER affiliate_id;
ALTER TABLE agents MODIFY earning DOUBLE NOT NULL;
ALTER TABLE agents MODIFY COLUMN earning DOUBLE DEFAULT 0;


-- update Person
ALTER TABLE persons DROP inheritor;
ALTER TABLE persons DROP referrer;
ALTER TABLE persons ADD COLUMN created_by VARCHAR(255) NOT NULL AFTER created_at;
ALTER TABLE persons DROP COLUMN created_by;
ALTER TABLE persons ADD COLUMN address VARCHAR(255) AFTER updated_at;
ALTER TABLE persons ADD COLUMN birthday date AFTER avatar;
ALTER TABLE persons ADD COLUMN gender VARCHAR(255) AFTER first_name;
ALTER TABLE persons ADD COLUMN province_id VARCHAR(255) AFTER phone;


-- create PriorityGroupAffiliate
DROP TABLE IF EXISTS `priority_groups_affiliates`;
CREATE TABLE `priority_groups_affiliates` (
	`id` VARCHAR (255) NOT NULL PRIMARY KEY,
	`created_at` datetime NOT NULL,
	`updated_at` datetime DEFAULT NULL,
	`affiliate_id` VARCHAR (255) NOT NULL,
	`priority_group_id` VARCHAR (255) NOT NULL,
  UNIQUE (affiliate_id, priority_group_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


-- update PriorityGroup
ALTER TABLE priority_groups DROP commission_percent;
ALTER TABLE priority_groups ADD commission FLOAT NOT NULL AFTER updated_at;
ALTER TABLE priority_groups MODIFY commission DOUBLE NOT NULL;


-- update Order
ALTER TABLE orders ADD COLUMN total FLOAT NOT NULL AFTER status;
ALTER TABLE orders MODIFY total DOUBLE NOT NULL;
ALTER TABLE order_lines MODIFY price DOUBLE NOT NULL;
ALTER TABLE orders ADD COLUMN earning DOUBLE AFTER discount_code_applied_id;


-- update Share
ALTER TABLE shares ADD COLUMN channel_id VARCHAR(255) NOT NULL AFTER link_id;
ALTER TABLE shares ADD COLUMN stats_date VARCHAR(12) NOT NULL AFTER link_id;


-- create Bill
DROP TABLE IF EXISTS bills;
CREATE TABLE bills (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  deadline    DATE,
  description TEXT,
  fee         FLOAT NOT NULL,
  subscriber_id   VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update ClickInfo
ALTER TABLE click_infos ADD COLUMN stats_date VARCHAR(12) NOT NULL AFTER share_id;


-- update Bill
ALTER TABLE bills MODIFY fee DOUBLE NOT NULL;
ALTER TABLE bills ADD COLUMN status VARCHAR(255) NOT NULL AFTER fee;


-- update Commission
ALTER TABLE commissions MODIFY earning DOUBLE NOT NULL;
ALTER TABLE commissions MODIFY value DOUBLE NOT NULL;
ALTER TABLE commissions ADD COLUMN subs_custom_config_applied_id VARCHAR(255) AFTER order_id;
ALTER TABLE commissions ADD COLUMN discount_code_applied_id VARCHAR(255) AFTER affiliate_id;


-- update DiscountCode
ALTER TABLE discount_codes MODIFY discount DOUBLE NOT NULL;


-- update DiscountCodeApplied
ALTER TABLE discount_codes_applied MODIFY discount DOUBLE NOT NULL;


-- update LayerCommissionConfig
ALTER TABLE layer_commission_configs MODIFY commission DOUBLE NOT NULL;


-- update Product
ALTER TABLE products MODIFY commission DOUBLE;
ALTER TABLE products MODIFY price DOUBLE NOT NULL;
ALTER TABLE products ADD COLUMN channel_id VARCHAR(255) AFTER updated_at;


-- update SubsConfig
ALTER TABLE subs_configs MODIFY account_keeping_fee DOUBLE;
ALTER TABLE subs_configs MODIFY lowest_payment DOUBLE;


-- update SubsCustomConfig
ALTER TABLE subs_custom_configs MODIFY commission DOUBLE NOT NULL;


-- create SubsCustomConfigApplied
DROP TABLE IF EXISTS subs_custom_configs_applied;
CREATE TABLE subs_custom_configs_applied (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  commission        DOUBLE NOT NULL,
  end_date          DATE,
  lower_condition   INTEGER,
  name              VARCHAR(255) NOT NULL,
  start_date        DATE,
  type              VARCHAR(255) NOT NULL,
  upper_condition   INTEGER
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Notification
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT 'UN_READ',
  `type` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `to_user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- create Bank
DROP TABLE IF EXISTS `banks`;
CREATE TABLE `banks` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255),
  `branch` varchar(255),
  `username` varchar(255),
  `number` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- rename SubsCustomConfig to SubsCommissionConfig
RENAME TABLE subs_custom_configs TO subs_commission_configs;
RENAME TABLE subs_custom_configs_applied TO subs_commission_configs_applied;
ALTER TABLE commissions CHANGE COLUMN subs_custom_config_applied_id subs_commission_config_applied_id VARCHAR(255);
ALTER TABLE layer_commission_configs CHANGE COLUMN subs_custom_config_id subs_commission_config_id VARCHAR(255);
ALTER TABLE priority_groups CHANGE COLUMN subs_custom_config_id subs_commission_config_id VARCHAR(255);


-- create indexes
CREATE INDEX by_email ON users (`email`);
CREATE INDEX by_status ON users (`status`);
CREATE INDEX by_nickname ON persons (`nickname`);
CREATE INDEX by_subscriber_id ON persons (`subscriber_id`);
CREATE INDEX by_affiliate_id ON agents (`affiliate_id`);
CREATE INDEX by_subscriber_id ON agents (`subscriber_id`);
