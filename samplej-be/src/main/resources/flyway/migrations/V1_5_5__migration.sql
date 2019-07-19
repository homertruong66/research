DELETE FROM subs_getfly_configs WHERE id IN (SELECT id FROM package_configs_applied where has_getfly = 0);
DELETE FROM subs_infusion_configs WHERE id IN (SELECT id FROM package_configs_applied where has_infusion = 0);
DELETE FROM subs_get_response_configs WHERE id IN (SELECT id FROM package_configs_applied where has_get_response = 0);