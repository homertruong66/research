-- /**
--  * homertruong
--  */

-- Version 1.4.x:   1. System Improvement
--                  2. Data Export

-- update SubsConfig
ALTER TABLE subs_configs DROP is_bank_transfer;
UPDATE subs_configs SET account_keeping_fee = 0 WHERE account_keeping_fee IS NULL;
UPDATE subs_configs SET lowest_payment = 0 WHERE lowest_payment IS NULL;


-- update Customer
ALTER TABLE customers ADD COLUMN metadata TEXT AFTER fullname;


-- update Product
ALTER TABLE products ADD CONSTRAINT unique_index_code_channel_id UNIQUE (code, channel_id);


-- update Notification
ALTER TABLE notifications ADD COLUMN target_id VARCHAR(255) AFTER status;


-- update Domain
ALTER TABLE domains CHANGE COLUMN area_id province_id VARCHAR (255);
UPDATE domains SET type = 'PROVINCE' WHERE type = 'AREA';


-- update Subscriber
ALTER TABLE subscribers DROP city;
ALTER TABLE subscribers ADD COLUMN province_id VARCHAR (255) NOT NULL AFTER phone;
UPDATE subscribers SET province_id = '606c757b-7f49-11e7-9e1f-966d5d8eadbc' WHERE province_id = '';


-- create SubsAlertConfig
DROP TABLE IF EXISTS `subs_alert_configs`;
CREATE TABLE `subs_alert_configs` (
	`id` VARCHAR (255) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime DEFAULT NULL,

	`content` LONGTEXT,
	`end_date` datetime,
	`start_date` datetime,
	`title` VARCHAR (255),
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

INSERT INTO subs_alert_configs (`id`, `created_at`, `content`, `end_date`, `start_date`)
SELECT id, now(), NULL, NULL, NULL FROM subscribers;


-- create SystemAlert
DROP TABLE IF EXISTS `system_alerts`;
CREATE TABLE `system_alerts` (
	`id` VARCHAR (255) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime DEFAULT NULL,

	`content` LONGTEXT,
	`end_date` datetime,
	`start_date` datetime,
	`title` VARCHAR (255),
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

INSERT INTO `system_alerts` (`id`, `created_at`, `content`, `title`)
VALUES
    ('1', now(), 'This is a system alert', 'SA1')
;


-- create Task
DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
  id              VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at      DATETIME     NOT NULL,
  updated_at      DATETIME     NULL,

  action VARCHAR(255) NOT NULL,
  params VARCHAR(255) NOT NULL,
  reason_task_created VARCHAR(255),
  reason_task_failed VARCHAR(255),
  status VARCHAR(255) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
