ALTER TABLE subs_configs ADD COLUMN is_bitly_enabled BIT DEFAULT 0 AFTER account_keeping_fee;

ALTER TABLE priority_groups MODIFY end_date datetime;
ALTER TABLE priority_groups MODIFY start_date datetime;

ALTER TABLE persons ADD COLUMN is_root_subs_admin BIT DEFAULT 0 AFTER is_phone_verified;

ALTER TABLE notifications DROP COLUMN url;

-- create ProductSet
DROP TABLE IF EXISTS `product_sets`;
CREATE TABLE `product_sets` (
	`id` VARCHAR (255) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime DEFAULT NULL,
	`commission` DOUBLE NOT NULL,
	`description` TEXT NOT NULL,
	`end_date` datetime,
	`name` VARCHAR (255) NOT NULL,
	`start_date` datetime,
	`subs_commission_config_id` VARCHAR (255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

-- create ProductSetProduct
DROP TABLE IF EXISTS `product_sets_products`;
CREATE TABLE `product_sets_products` (
	`id` VARCHAR (255) NOT NULL PRIMARY KEY,
	`created_at` datetime NOT NULL,
	`updated_at` datetime DEFAULT NULL,
	`product_id` VARCHAR (255) NOT NULL,
	`product_set_id` VARCHAR (255) NOT NULL,
  UNIQUE (product_id, product_set_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

ALTER TABLE package_configs ADD COLUMN has_commission_cops BIT DEFAULT 0 AFTER has_commission_coppr;
ALTER TABLE package_configs_applied ADD COLUMN has_commission_cops BIT DEFAULT 0 AFTER has_commission_coppr;
UPDATE package_configs SET has_commission_cops = 1 WHERE type IN ("VIP","UP");
UPDATE package_configs_applied SET has_commission_cops = 1 WHERE type IN ("VIP","UP");

ALTER TABLE condition_commission_configs ADD COLUMN product_id VARCHAR(255) AFTER lower_condition;
ALTER TABLE subs_commission_configs_applied ADD COLUMN product_id VARCHAR(255) AFTER name;
ALTER TABLE package_configs ADD COLUMN has_commission_copq BIT DEFAULT 0 AFTER has_commission_coppr;
ALTER TABLE package_configs_applied ADD COLUMN has_commission_copq BIT DEFAULT 0 AFTER has_commission_coppr;
UPDATE package_configs SET has_commission_copq = 1 WHERE type IN ("VIP","UP");
UPDATE package_configs_applied SET has_commission_copq = 1 WHERE type IN ("VIP","UP");

-- create default data for SubsCommissionConfig COPS, COPQ
INSERT INTO subs_commission_configs (id, created_at, commission, is_enabled, name, subscriber_id, type)
SELECT uuid(), now(), 0, 1, 'Commission On Product Set', id, 'COPS' FROM package_configs_applied WHERE type IN ("VIP","UP");
INSERT INTO subs_commission_configs (id, created_at, commission, is_enabled, name, subscriber_id, type)
SELECT uuid(), now(), 0, 1, 'Commission On Product Quantity', id, 'COPQ' FROM package_configs_applied WHERE type IN ("VIP","UP");

ALTER TABLE orders ADD COLUMN is_customer_converted BIT DEFAULT 0 AFTER earning;

UPDATE package_configs_applied pca SET created_at = (SELECT created_at FROM subscribers s WHERE pca.id = s.id);

ALTER TABLE tasks MODIFY params TEXT;

ALTER TABLE priority_groups CHANGE group_name name VARCHAR(255) NOT NULL;