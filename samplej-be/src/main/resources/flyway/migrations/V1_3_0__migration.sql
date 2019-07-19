-- /**
--  * homertruong
--  */

-- Version 1.3.x:   1. Infusion/Getfly Integration
--                  2. Reward Management
--                  3. Performer Management
--                  4. Voucher Management
--                  5. Feedback Management
--                  6. System Improvement

-- create PackageConfig
DROP TABLE IF EXISTS package_configs;
CREATE TABLE package_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_count INTEGER,
  channel_count INTEGER,
  has_mobile_app BIT DEFAULT 0,
  has_commission_cad BIT DEFAULT 0,
  has_commission_cfe BIT DEFAULT 0,
  has_commission_coan BIT DEFAULT 0,
  has_commission_coasv BIT DEFAULT 0,
  has_commission_coov BIT DEFAULT 0,
  has_commission_copg BIT DEFAULT 0,
  has_commission_copp BIT DEFAULT 0,
  has_commission_coppr BIT DEFAULT 0,
  has_discount_code BIT DEFAULT 0,
  has_getfly BIT DEFAULT 0,
  has_infusion BIT DEFAULT 0,
  has_share_stats BIT DEFAULT 0,
  has_voucher BIT DEFAULT 0,
  layer_count INTEGER,
  type VARCHAR(255) DEFAULT 'DEMO',
  usage_period VARCHAR(255) DEFAULT 'MONTH'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create PackageConfigApplied
DROP TABLE IF EXISTS package_configs_applied;
CREATE TABLE package_configs_applied (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_count INTEGER,
  channel_count INTEGER,
  has_mobile_app BIT DEFAULT 0,
  has_commission_cad BIT DEFAULT 0,
  has_commission_cfe BIT DEFAULT 0,
  has_commission_coan BIT DEFAULT 0,
  has_commission_coasv BIT DEFAULT 0,
  has_commission_coov BIT DEFAULT 0,
  has_commission_copg BIT DEFAULT 0,
  has_commission_copp BIT DEFAULT 0,
  has_commission_coppr BIT DEFAULT 0,
  has_discount_code BIT DEFAULT 0,
  has_getfly BIT DEFAULT 0,
  has_infusion BIT DEFAULT 0,
  has_share_stats BIT DEFAULT 0,
  has_voucher BIT DEFAULT 0,
  layer_count INTEGER,
  type VARCHAR(255) DEFAULT 'DEMO',
  usage_expired_at DATETIME
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create SubsPackageConfig
DROP TABLE IF EXISTS subs_package_configs;
CREATE TABLE subs_package_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_count INTEGER,
  channel_count INTEGER,
  has_voucher BIT DEFAULT 0
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create default data for SubsPackageConfig
INSERT INTO subs_package_configs (`id`, `created_at`, `affiliate_count`, `channel_count`, `has_voucher`)
SELECT id, now(), NULL, NULL, 0 FROM subscribers;


-- create SubsInfusionConfig
DROP TABLE IF EXISTS subs_infusion_configs;
CREATE TABLE subs_infusion_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME   NOT NULL,
  updated_at DATETIME   NULL,

  client_id VARCHAR(255),
  client_secret VARCHAR(255)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create default data for SubsInfusionConfig
INSERT INTO subs_infusion_configs (`id`, `created_at`)
SELECT id, now() FROM subscribers;


-- create SubsGetflyConfig
DROP TABLE IF EXISTS subs_getfly_configs;
CREATE TABLE subs_getfly_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME   NOT NULL,
  updated_at DATETIME   NULL,

  api_key VARCHAR(255)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create default data for SubsGetflyConfig
INSERT INTO subs_getfly_configs (`id`, `created_at`)
SELECT id, now() FROM subscribers;


-- update Subscriber
ALTER TABLE subscribers ADD COLUMN number_of_affiliates BIGINT AFTER mobile_phone;
ALTER TABLE subscribers ADD COLUMN status VARCHAR (255) DEFAULT 'ENABLED' NOT NULL AFTER phone;


-- update Affiliate
ALTER TABLE persons ADD COLUMN number_of_affiliates_in_network BIGINT AFTER nickname;


-- update SubsInfusionConfig
ALTER TABLE subs_infusion_configs ADD COLUMN access_token VARCHAR(255) AFTER updated_at;
ALTER TABLE subs_infusion_configs ADD COLUMN expired_date DATETIME AFTER client_secret;
ALTER TABLE subs_infusion_configs ADD COLUMN refresh_token VARCHAR(255) AFTER expired_date;


-- update Product
ALTER TABLE products ADD COLUMN code VARCHAR (255) NOT NULL AFTER channel_id;


-- update Order
ALTER TABLE orders ADD COLUMN infusion_tag_ids VARCHAR (255) AFTER earning;


-- update SubsGetflyConfig
ALTER TABLE subs_getfly_configs ADD COLUMN base_url VARCHAR (255) AFTER api_key;


-- create SubsEarnerConfig
DROP TABLE IF EXISTS `subs_earner_configs`;
CREATE TABLE `subs_earner_configs` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `click_count` INTEGER DEFAULT 300,
  `cycle` varchar(255) COLLATE utf8_bin DEFAULT 'MONTH',
  `direct_network_revenue` BIGINT DEFAULT 30000000,
  `indirect_network_revenue` BIGINT DEFAULT 50000000,
  `new_affiliate_count` INTEGER DEFAULT 5,
  `new_customer_count` INTEGER DEFAULT 5,
  `new_order_count` INTEGER DEFAULT 10,
  `revenue` BIGINT DEFAULT 10000000,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- create default data for SubsEarnerConfig
INSERT INTO subs_earner_configs (`id`, `created_at`) SELECT id, now() FROM subscribers;


-- create ConditionCommissionConfig
DROP TABLE IF EXISTS condition_commission_configs;
CREATE TABLE condition_commission_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  commission        DOUBLE NOT NULL,
  end_date          DATETIME,
  lower_condition   INTEGER,
  start_date        DATETIME,
  subs_commission_config_id VARCHAR(255) NOT NULL,
  upper_condition   INTEGER
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


-- update SubsCommissionConfig
ALTER TABLE subs_commission_configs DROP COLUMN start_date;
ALTER TABLE subs_commission_configs DROP COLUMN end_date;
ALTER TABLE subs_commission_configs DROP COLUMN lower_condition;
ALTER TABLE subs_commission_configs DROP COLUMN upper_condition;


-- update Product
ALTER TABLE products DROP COLUMN commission;


-- update OrderLine
ALTER TABLE order_lines ADD COLUMN commission DOUBLE AFTER updated_at;


-- create SubsPackageStatus
DROP TABLE IF EXISTS subs_package_status;
CREATE TABLE subs_package_status (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_count INTEGER DEFAULT 0,
  channel_count INTEGER DEFAULT 0,
  layer_count INTEGER DEFAULT 0
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO subs_package_status (`id`, `created_at`)
SELECT id, now() FROM subscribers;


-- create Voucher
DROP TABLE IF EXISTS vouchers;
CREATE TABLE vouchers (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  benefit         DOUBLE       NOT NULL,
  code            VARCHAR(255) NOT NULL,
  end_date        DATETIME,
  name            VARCHAR(255) NOT NULL,
  start_date      DATETIME     NOT NULL,
  status          VARCHAR(255) NOT NULL,
  subscriber_id   VARCHAR(255) NOT NULL,
  type            VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update SubsCommissionConfigApplied
ALTER TABLE subs_commission_configs_applied ADD COLUMN subscriber_id VARCHAR (255) NOT NULL AFTER start_date;


-- create SubsPerformerConfig
DROP TABLE IF EXISTS `subs_performer_configs`;
CREATE TABLE `subs_performer_configs` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,

  `click_count` INTEGER DEFAULT 300,
  `cycle` varchar(255) COLLATE utf8_bin DEFAULT 'MONTH',
  `direct_network_revenue` BIGINT DEFAULT 30000000,
  `indirect_network_revenue` BIGINT DEFAULT 50000000,
  `new_affiliate_count` INTEGER DEFAULT 5,
  `new_customer_count` INTEGER DEFAULT 5,
  `new_order_count` INTEGER DEFAULT 10,
  `revenue` BIGINT DEFAULT 10000000,
  `top_n` INTEGER,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO subs_performer_configs (`id`, `created_at`)
SELECT id, now() FROM subscribers;


-- create SubsRewardConfig
DROP TABLE IF EXISTS `subs_reward_configs`;
CREATE TABLE `subs_reward_configs` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,

  `click_count` INTEGER DEFAULT 300,
  `cycle` varchar(255) COLLATE utf8_bin DEFAULT 'MONTH',
  `direct_network_revenue` BIGINT DEFAULT 30000000,
  `gift` varchar(255) COLLATE utf8_bin,
  `indirect_network_revenue` BIGINT DEFAULT 50000000,
  `money` DOUBLE,
  `new_affiliate_count` INTEGER DEFAULT 5,
  `new_customer_count` INTEGER DEFAULT 5,
  `new_order_count` INTEGER DEFAULT 10,
  `revenue` BIGINT DEFAULT 10000000,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO subs_reward_configs (`id`, `created_at`)
SELECT id, now() FROM subscribers;


-- create AffiliateVoucher
DROP TABLE IF EXISTS affiliates_vouchers;
CREATE TABLE affiliates_vouchers (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_id   VARCHAR(255) NOT NULL,
  voucher_id   VARCHAR(255) NOT NULL,
  CONSTRAINT unique_index_affiliate_voucher_id
  UNIQUE (affiliate_id, voucher_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update Order
ALTER TABLE orders CHANGE infusion_tag_ids infusion_tags VARCHAR(255);
ALTER TABLE orders ADD COLUMN is_getfly_success BIT AFTER earning;
ALTER TABLE orders ADD COLUMN is_infusion_success BIT AFTER is_getfly_success;


-- create Performer
DROP TABLE IF EXISTS `performers`;
CREATE TABLE `performers` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,

  `affiliate_id` VARCHAR (255),
  `click_count` INTEGER,
  `cycle` varchar(255),
  `direct_network_revenue` BIGINT,
  `first_name` VARCHAR (255) NOT NULL,
  `indirect_network_revenue` BIGINT,
  `last_name` VARCHAR (255) ,
  `new_affiliate_count` INTEGER,
  `new_customer_count` INTEGER,
  `new_order_count` INTEGER,
  `nickname` VARCHAR (255) NOT NULL,
  `revenue` BIGINT,
  `subscriber_domain_name` VARCHAR (255) NOT NULL,
  `subscriber_id` VARCHAR (255) NOT NULL,
  `subscriber_name` VARCHAR (255) NOT NULL,
  `top_n` INTEGER,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- update PackageConfig
UPDATE package_configs SET type = 'TRIAL' WHERE type = 'DEMO';
ALTER TABLE package_configs ADD COLUMN price DOUBLE NOT NULL AFTER layer_count;
UPDATE package_configs SET price = 0 WHERE type = 'TRIAL';
UPDATE package_configs SET price = 12000000 WHERE type = 'BASIC';
UPDATE package_configs SET price = 35000000 WHERE type = 'VIP';
UPDATE package_configs SET price = 59000000 WHERE type = 'UP';


-- update PackageConfigApplied
ALTER TABLE package_configs_applied ADD COLUMN usage_period VARCHAR(255) NOT NULL DEFAULT 'MONTH';
ALTER TABLE package_configs_applied ADD COLUMN price DOUBLE NOT NULL AFTER layer_count;
UPDATE package_configs_applied SET price = 0 WHERE type = 'TRIAL';
UPDATE package_configs_applied SET price = 12000000 WHERE type = 'BASIC';
UPDATE package_configs_applied SET price = 35000000 WHERE type = 'VIP';
UPDATE package_configs_applied SET price = 59000000 WHERE type = 'UP';


-- update SubsPackageConfig
UPDATE subs_package_configs spc SET spc.affiliate_count = 0, spc.channel_count = 0, spc.has_voucher = 0;


-- create Feedback
DROP TABLE IF EXISTS feedbacks;
CREATE TABLE feedbacks (
  id            VARCHAR(255) NOT NULL,
  created_at    DATETIME     NOT NULL,
  updated_at    DATETIME,
  created_by    VARCHAR(255) NOT NULL,

  description   VARCHAR(255) NOT NULL,
  status        VARCHAR(255) NOT NULL,
  title         VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- update InfusionConfig
ALTER TABLE subs_infusion_configs DROP client_id;
ALTER TABLE subs_infusion_configs DROP client_secret;
