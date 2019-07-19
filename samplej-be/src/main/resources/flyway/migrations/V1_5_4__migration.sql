ALTER TABLE persons ADD COLUMN is_get_response_success BIT AFTER is_phone_verified;
ALTER TABLE persons ADD COLUMN metadata TEXT AFTER is_root_subs_admin;