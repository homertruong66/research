ALTER TABLE orders ADD COLUMN is_get_response_success BIT AFTER is_getfly_success;
ALTER TABLE package_configs ADD COLUMN has_get_response BIT DEFAULT 0 AFTER has_getfly;
ALTER TABLE package_configs_applied ADD COLUMN has_get_response BIT DEFAULT 0 AFTER has_getfly;
UPDATE package_configs SET has_get_response = 1 WHERE type IN ("VIP","UP");
UPDATE package_configs_applied SET has_get_response = 1 WHERE type IN ("VIP","UP");

-- create SubsGetResponseConfig
DROP TABLE IF EXISTS subs_get_response_configs;
CREATE TABLE subs_get_response_configs (
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at DATETIME   NOT NULL,
  updated_at DATETIME   NULL,

  api_key VARCHAR(255),
  campaign_default_id VARCHAR(255),
  send_affiliate_data BIT DEFAULT 0
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

  -- create default data for SubsGetResponseConfig
INSERT INTO subs_get_response_configs (`id`, `created_at`)
SELECT id, now() FROM subscribers;