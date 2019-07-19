SET NAMES utf8;

-- Role
INSERT INTO `roles` (`id`, `name`, `created_at`)
VALUES
    ('1', 'ROLE_ADMIN', '2018-05-15 06:06:00' ),
    ('2', 'ROLE_AFFILIATE', '2018-05-15 06:06:00'),
    ('3', 'ROLE_CHANNEL', '2018-05-15 06:06:00'),
    ('4', 'ROLE_SUBS_ADMIN', '2018-05-15 06:06:00'),
    ('5', 'ROLE_ACCOUNTANT', '2018-05-15 06:06:00')
;


-- Person: Admin
INSERT INTO `persons` (
    `id`, `created_at`, `discriminator`, `first_name`, `last_name`)
VALUES
    ('1Admin78-8e2e-4727-b8b4-92a6b950ba01', '2018-05-15 06:06:00', 'Admin', 'RMS', 'Admin'),
    ('1Admin78-8e2e-4727-b8b4-92a6b950ba02', '2018-05-15 06:06:00', 'Admin', 'DevOps', 'Admin')
;


-- User 1:1 Person
INSERT INTO `users` (`id`, `created_at`, `email`, `password`, `status`)
VALUES
    -- Admin
    ('1Admin78-8e2e-4727-b8b4-92a6b950ba01', '2018-05-15 06:06:00', 'rmsadmin@rms.com.vn', '$2a$12$Jk8DVDcmuukKEbTECJJOmuerlMvQ5GGzlI.nbwqKlxRpJP3ejnKaS', 'ACTIVE'),
    ('1Admin78-8e2e-4727-b8b4-92a6b950ba02', '2018-05-15 06:06:00', 'devopsadmin@rms.com.vn', '$2a$12$Ghubm5YjSKt0cUbGKzIg4.fOdVtp8x4VM.is.c84l7fCRA9YuQVIG', 'ACTIVE')
;


-- UserRole
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    -- ADMIN
    ('1Admin78-8e2e-4727-b8b4-92a6b950ba01', '1'),
    ('1Admin78-8e2e-4727-b8b4-92a6b950ba02', '1')
;


-- PackageConfig
INSERT INTO package_configs (
  `id`, `created_at`, `affiliate_count`, `channel_count`, `has_mobile_app`,
  `has_commission_cad`, `has_commission_cfe`, `has_commission_coan`, `has_commission_coasv`, `has_commission_coov`,
  `has_commission_copg`, `has_commission_copp`, `has_commission_coppr`,  `has_commission_copq`, `has_commission_cops`, `has_discount_code`,
  `has_getfly`, `has_infusion`, `has_share_stats`, `has_voucher`, `layer_count`, `price`, `type`, `usage_period`)
VALUES
  -- TRIAL
  ('1PacCo78-Se2e-4727-b8b4-92a6b950ba01', '2018-09-006 06:06:00', 20, 1, 0,
   1, 0, 1, 0, 0,
   0, 0, 0, 0, 0, 0,
   0, 0, 1, 0, 3, 0, 'TRIAL', 'MONTH'
  ),
  -- BASIC
  ('1PacCo78-Se2e-4727-b8b4-92a6b950ba02', '2018-09-006 06:06:00', 100, 2, 0,
   1, 0, 1, 0, 0,
   0, 1, 1, 0, 0, 1,
   0, 1, 1, 1, 3, 12000000, 'BASIC', 'YEAR'
  ),
  -- VIP
  ('1PacCo78-Se2e-4727-b8b4-92a6b950ba03', '2018-09-006 06:06:00', 300, 3, 1,
   1, 0, 1, 1, 1,
   1, 1, 1, 1, 1, 1,
   1, 1, 1, 1, 5, 35000000, 'VIP', 'YEAR'
  ),
  -- UP
  ('1PacCo78-Se2e-4727-b8b4-92a6b950ba04', '2018-09-006 06:06:00', 500, 5, 1,
   1, 1, 1, 1, 1,
   1, 1, 1, 1, 1, 1,
   1, 1, 1, 1, 7, 59000000, 'UP', 'YEAR'
  )
;


-- Guide
INSERT INTO `guides` (
    `id`, `created_at`, `content`, `note`, `target`, `title`)
VALUES
    ('1Guide78-Se2e-4727-b8b4-92a6b950ba01', '2018-05-15 06:06:00', 'SUBS_ADMIN sử dụng hệ thống', 'Note', 'SUBS_ADMIN', 'Hướng dẫn sử dụng hệ thống cho SUBS_ADMIN'),
    ('1Guide78-Se2e-4727-b8b4-92a6b950ba02', '2018-05-15 06:06:00', 'AFFILIATE sử dụng hệ thống', 'Note', 'AFFILIATE', 'Hướng dẫn sử dụng hệ thống cho AFFILIATE')
;
