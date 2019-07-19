ALTER TABLE subs_get_response_configs ADD COLUMN address_field_id VARCHAR(255) AFTER send_affiliate_data;
ALTER TABLE subs_get_response_configs ADD COLUMN birthday_field_id VARCHAR(255) AFTER address_field_id;
ALTER TABLE subs_get_response_configs ADD COLUMN facebook_link_field_id VARCHAR(255) AFTER birthday_field_id;
ALTER TABLE subs_get_response_configs ADD COLUMN password_field_id VARCHAR(255) AFTER facebook_link_field_id;
ALTER TABLE subs_get_response_configs ADD COLUMN phone_field_id VARCHAR(255) AFTER password_field_id;
ALTER TABLE subs_get_response_configs ADD COLUMN referrer_email_field_id VARCHAR(255) AFTER phone_field_id;