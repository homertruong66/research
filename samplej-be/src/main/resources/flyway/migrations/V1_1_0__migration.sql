-- /**
--  * homertruong
--  */

-- Version 1.1.x: Affiliate, Post, Share, Customer, Order, Commission Management, and Discount Code

-- create Post
DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  content    TEXT,
  description TEXT,
  subs_admin_id VARCHAR(255) NOT NULL,
  thumbnail  TEXT,
  title      TEXT,
  url        TEXT NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update Post
ALTER TABLE posts MODIFY COLUMN content LONGTEXT;


-- update Person
ALTER TABLE persons MODIFY COLUMN is_phone_verified BIT DEFAULT 0;

UPDATE persons
SET discriminator = 'WEB_PLUGIN', first_name = 'Web Plugin F', last_name = 'L'
WHERE discriminator = 'CHANNEL';

UPDATE persons SET is_phone_verified = 0 WHERE is_phone_verified IS NULL;


-- update Role
UPDATE roles
SET name = 'ROLE_WEB_PLUGIN'
WHERE name = 'ROLE_CHANNEL';


-- create SubsCustomConfig
DROP TABLE IF EXISTS subs_custom_configs;
CREATE TABLE subs_custom_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  commission        FLOAT NOT NULL,
  end_date          DATE,
  is_enabled        BIT DEFAULT 0,
  lower_condition   INTEGER,
  name              VARCHAR(255) NOT NULL,
  start_date        DATE,
  subscriber_id     VARCHAR(255) NOT NULL,
  type              VARCHAR(255) NOT NULL,
  upper_condition   INTEGER
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Link
DROP TABLE IF EXISTS links;
CREATE TABLE links (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  description TEXT,
  thumbnail  TEXT,
  title      TEXT,
  url        TEXT NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Share
DROP TABLE IF EXISTS shares;
CREATE TABLE shares (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  affiliate_id  VARCHAR(255),
  link_id       VARCHAR(255),
  click_count   INTEGER,
  CONSTRAINT unique_index_affiliate_link_id
  UNIQUE (affiliate_id, link_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create ClickInfo
DROP TABLE IF EXISTS click_infos;
CREATE TABLE click_infos (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  country       VARCHAR(255) NOT NULL,
  device_type   VARCHAR(255) NOT NULL,
  os            VARCHAR(255) NOT NULL,
  share_id      VARCHAR(255) NOT NULL,
  count         INTEGER DEFAULT 0,
  CONSTRAINT unique_index_country_device_type_os_share_id
  UNIQUE (country, device_type, os, share_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create DiscountCode
DROP TABLE IF EXISTS discount_codes;
CREATE TABLE discount_codes (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_id      VARCHAR(255) NOT NULL,
  code              VARCHAR(255) NOT NULL,
  discount          FLOAT NOT NULL,
  end_date          DATE,
  note              TEXT,
  start_date        DATE NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Commission
DROP TABLE IF EXISTS commissions;
CREATE TABLE commissions (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME,

  affiliate_id    VARCHAR(255) NOT NULL,
  order_id        VARCHAR(255),
  subscriber_id   VARCHAR(255) NOT NULL,
  type            VARCHAR(255) NOT NULL,
  value           FLOAT
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Guide
DROP TABLE IF EXISTS guides;
CREATE TABLE guides (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME,

  content         LONGTEXT NOT NULL,
  note            TEXT,
  target          VARCHAR(255) NOT NULL,
  title           VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Customer
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME,

  address         VARCHAR(255),
  email           VARCHAR(255) NOT NULL,
  fullname        VARCHAR(255) NOT NULL,
  phone           VARCHAR(255)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create DiscountCodeApplied
DROP TABLE IF EXISTS discount_codes_applied;
CREATE TABLE discount_codes_applied (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_id      VARCHAR(255) NOT NULL,
  code              VARCHAR(255) NOT NULL,
  discount          FLOAT NOT NULL,
  end_date          DATE,
  note              TEXT,
  start_date        DATE NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Order
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_id      VARCHAR(255) NOT NULL,
  channel_id        VARCHAR(255) NOT NULL,
  customer_id       VARCHAR(255) NOT NULL,
  discount_code_applied_id  VARCHAR(255),
  note              TEXT,
  reason            TEXT,
  status            VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create OrderLine
DROP TABLE IF EXISTS order_lines;
CREATE TABLE order_lines (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  order_id          VARCHAR(255) NOT NULL,
  product_id        VARCHAR(255) NOT NULL,
  price             FLOAT NOT NULL,
  quantity          INTEGER NOT NULL,
  CONSTRAINT unique_order_product_id
  UNIQUE (order_id, product_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Product
DROP TABLE IF EXISTS products;
CREATE TABLE products (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  description       LONGTEXT,
  image             TEXT,
  name              VARCHAR(255) NOT NULL,
  price             FLOAT NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update Role
UPDATE roles SET created_at = '2018-05-15 06:06:00' WHERE created_at IS NULL;
ALTER TABLE roles MODIFY created_at DATETIME NOT NULL;


-- update Post
ALTER TABLE posts MODIFY created_at DATETIME NOT NULL;


-- update Link, Share, ClickInfo
ALTER TABLE links MODIFY created_at DATETIME NOT NULL;
ALTER TABLE shares MODIFY created_at DATETIME NOT NULL;
ALTER TABLE click_infos MODIFY created_at DATETIME NOT NULL;


-- update SubsCustomConfig
ALTER TABLE subs_custom_configs MODIFY created_at DATETIME NOT NULL;
ALTER TABLE subs_custom_configs MODIFY end_date DATETIME;
ALTER TABLE subs_custom_configs MODIFY start_date DATETIME;


-- update DiscountCode
ALTER TABLE discount_codes MODIFY end_date DATETIME;
ALTER TABLE discount_codes MODIFY start_date DATETIME NOT NULL;


-- update DiscountCodeApplied
ALTER TABLE discount_codes_applied MODIFY end_date DATETIME;
ALTER TABLE discount_codes_applied MODIFY start_date DATETIME NOT NULL;


-- create SubsConfig
DROP TABLE IF EXISTS subs_configs;
CREATE TABLE subs_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  account_keeping_fee   FLOAT,
  is_bank_transfer      BIT DEFAULT 1,
  logo                  TEXT,
  lowest_payment        FLOAT,
  terms_and_conditions  TEXT
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update Person
ALTER TABLE persons ADD COLUMN domain_name VARCHAR(255);


-- update Share
ALTER TABLE shares ADD COLUMN url TEXT NOT NULL;


-- create Payment
DROP TABLE IF EXISTS payments;
CREATE TABLE payments (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  affiliate_id    VARCHAR(255) NOT NULL,
  subscriber_id   VARCHAR(255) NOT NULL,
  status          VARCHAR(255) NOT NULL,
  value           FLOAT NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create SubsEmailConfig
DROP TABLE IF EXISTS subs_email_configs;
CREATE TABLE subs_email_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME   NOT NULL,
  updated_at DATETIME   NULL,

  email     VARCHAR(255),
  email_reply_to VARCHAR(255),
  hostname  VARCHAR(255),
  password  VARCHAR(255),
  port      INTEGER
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create SubsEmailTemplate
DROP TABLE IF EXISTS subs_email_templates;
CREATE TABLE subs_email_templates (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME   NOT NULL,
  updated_at DATETIME   NULL,

  content TEXT NOT NULL,
  is_enabled BIT DEFAULT 1,
  subs_email_config_id VARCHAR(255) NOT NULL,
  title  VARCHAR(255) NOT NULL,
  type  VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update SubsConfig
ALTER TABLE subs_configs ADD COLUMN coan_type VARCHAR(255) NOT NULL;


-- update Order
ALTER TABLE orders MODIFY affiliate_id VARCHAR(255) NULL;
ALTER TABLE orders ADD COLUMN number VARCHAR(255) NOT NULL AFTER note;
ALTER TABLE orders ADD COLUMN created_by VARCHAR(255) NOT NULL AFTER created_at;


-- update Product
ALTER TABLE products ADD COLUMN commission FLOAT AFTER updated_at;


-- create LayerCommissionConfig
DROP TABLE IF EXISTS layer_commission_configs;
CREATE TABLE layer_commission_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  commission            FLOAT NOT NULL,
  layer                 INTEGER NOT NULL,
  subs_custom_config_id VARCHAR (255) NOT NULL
)
