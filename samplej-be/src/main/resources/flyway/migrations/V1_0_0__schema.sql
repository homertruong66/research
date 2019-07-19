-- /**
--  * homertruong
--  */

-- Version 1.0.x: System Security, Subscriber Management, Channel Management and Affiliate Management

-- create User
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME     NULL,

  activation_code TEXT NULL,
  email           VARCHAR(255) NOT NULL,
  password        VARCHAR(255) NOT NULL,
  status          VARCHAR(255)  NOT NULL,
  token           TEXT         NULL,
  CONSTRAINT users_email_uindex
  UNIQUE (email)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create UserRole
DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
  user_id VARCHAR(255) NOT NULL,
  role_id VARCHAR(255) NOT NULL,
  CONSTRAINT unique_index_user_role_id
  UNIQUE (user_id, role_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Role
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NULL,
  updated_at DATETIME     NULL,

  name       VARCHAR(255) NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Person
DROP TABLE IF EXISTS persons;
CREATE TABLE persons (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  discriminator   VARCHAR(255) NOT NULL,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME,

  -- Person
  avatar          TEXT,
  email           VARCHAR(255) NOT NULL,
  first_name      VARCHAR(255) NOT NULL,
  last_name       VARCHAR(255) NOT NULL,
  phone           VARCHAR(255),

  -- Admin

  -- Affiliate
  is_phone_verified BIT,

  -- SubsAdmin
  subscriber_id VARCHAR(255)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create UploadError
DROP TABLE IF EXISTS upload_errors;
CREATE TABLE upload_errors (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME     NULL,

  domain_object_type           VARCHAR (255),
  error                        TEXT
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Subscriber
DROP TABLE IF EXISTS subscribers;
CREATE TABLE subscribers (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME,

  address         TEXT NOT NULL,
  city            VARCHAR(255) NOT NULL,
  company_name    TEXT NOT NULL,
  district        VARCHAR(255) NOT NULL,
  domain_name     VARCHAR(255) NOT NULL,
  email           VARCHAR(255),
  mobile_phone    VARCHAR(255) NOT NULL,
  package_type    VARCHAR(255) NOT NULL,
  phone           VARCHAR(255),
  website         TEXT NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Agent
DROP TABLE IF EXISTS agents;
CREATE TABLE agents (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME,

  subscriber_id   VARCHAR(255) NOT NULL,
  affiliate_id    VARCHAR(255) NOT NULL,
  CONSTRAINT agents_subscriber_id_affiliate_id_uindex
  UNIQUE (subscriber_id, affiliate_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- create Domain
DROP TABLE IF EXISTS domains;
CREATE TABLE domains (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  area_id    VARCHAR(255) NULL,
  code       VARCHAR(255) NULL,
  country_id VARCHAR(255) NULL,
  name       VARCHAR(255) NULL,
  status     VARCHAR(255) NULL,
  type       VARCHAR(255) NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- update Person
ALTER TABLE persons DROP COLUMN email;
