-- Role
INSERT INTO `roles` (`id`, `name`) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `roles` (`id`, `name`) VALUES ('2', 'ROLE_USER');
INSERT INTO `roles` (`id`, `name`) VALUES ('3', 'ROLE_STUDENT');
INSERT INTO `roles` (`id`, `name`) VALUES ('4', 'ROLE_TEACHER');

-- User
INSERT INTO `users` (`discriminator`, `id`, `age`, `created_at`, `email`, `name`, `password`, `updated_at`, `VERSION`, `department_id`, `year`, `degree`) VALUES ('USER', '81b17890-ff51-4180-a4ed-28a74cfae3cd', 66, '2017-03-04 20:47:58', 'admin@3s', 'Admin', '$2a$12$TQPs4NOccDydGr5LYPhXg.K.nXOeNqU4oUsFJi59fTAsNPhl.wcLu', '2017-03-04 20:47:58', 0, NULL, NULL, NULL);
INSERT INTO `users` (`discriminator`, `id`, `age`, `created_at`, `email`, `name`, `password`, `token`, `updated_at`, `VERSION`, `department_id`, `year`, `degree`) VALUES ('USER', '17fb512c-4a64-4b9d-8a13-d9c8b28c7fd3', 1, '2017-04-02 16:37:09', 'homer@3s', 'U1', '$2a$12$RwGGCqWkjZ3yFrlx4pR4qOBj17nSqozuMB9708U/47JxfdtpClnHS', NULL, '2017-04-02 16:37:09', 0, NULL, NULL, NULL);

-- UserRole
INSERT INTO `users_roles` (`id`, `role_id`, `user_id`) VALUES ('141b1142-c370-4946-b786-62dfcb529d13', '1', '81b17890-ff51-4180-a4ed-28a74cfae3cd');

-- Domain
INSERT INTO `domains` (`id`, `code`, `name`, `type`) VALUES ('1', 'VNI', 'Vietnam', 'Country');
INSERT INTO `domains` (`id`, `code`, `name`, `type`) VALUES ('2', 'CAN', 'Canada', 'Country');
INSERT INTO `domains` (`id`, `code`, `name`, `type`) VALUES ('3', 'USA', 'United States of America', 'Country');
