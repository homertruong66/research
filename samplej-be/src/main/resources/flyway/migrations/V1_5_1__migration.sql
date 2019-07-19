ALTER TABLE persons DROP number_of_affiliates_in_network;
ALTER TABLE agents ADD COLUMN number_of_affiliates_in_network BIGINT AFTER inheritor;