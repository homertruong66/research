SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE USER_ROLE;
TRUNCATE TABLE ROLE;
TRUNCATE TABLE USER;
TRUNCATE TABLE COUNTRY;
TRUNCATE TABLE PROVINCE;
TRUNCATE TABLE PERSON;
TRUNCATE TABLE ORGANIZATION;
TRUNCATE TABLE PARTY;
TRUNCATE TABLE RECIPIENT;
TRUNCATE TABLE ITEM;
TRUNCATE TABLE MAILER;
TRUNCATE TABLE MAIL;
TRUNCATE TABLE VOTE;
TRUNCATE TABLE PLACE;
TRUNCATE TABLE EVENT;

INSERT INTO USER (ID, ENTITY_DISCRIMINATOR, VERSION, USER_NAME, PASSWORD, ENABLED, IS_BELONG_ORGANIZATION, FULL_NAME, EMAIL, PARTY_ID)
    VALUES
        (1 , 'USER'  , 0, 'Hoang', 'hoang'  , 1, 0, '', '', 1),
        (2 , 'USER'  , 0, 'Hung' , 'hung'   , 1, 0, '', '', 2),
        (3 , 'USER'  , 0, 'Vien' , 'vien'   , 1, 0, '', '', 3),
        (4 , 'USER'  , 0, 'Vinh' , 'vinh'   , 1, 0, '', '', 4),
        (5 , 'USER'  , 0, 'Thao' , 'thao'   , 1, 0, '', '', 5),
        (6 , 'USER'  , 0, 'Uyen' , 'uyen'   , 1, 0, '', '', 6),
        (7 , 'USER'  , 0, 'Tram' , 'tram'   , 1, 0, '', '', 7),
        (8 , 'USER'  , 0, 'YGS'  , 'ygs'    , 1, 1, '', '', 8),
        (9 , 'USER'  , 0, 'NSBN' , 'nsbn'   , 1, 1, '', '', 9),
        (10, 'USER'  , 0, 'AAA'  , 'aaa'    , 0, 1, '', '', 10),
        (11, 'USER'  , 0, 'BBB'  , 'bbb'    , 1, 1, '', '', 11),
        (12, 'USER'  , 0, 'CCC'  , 'ccc'    , 0, 1, '', '', 12),
        (13, 'USER'  , 0, 'DDD'  , 'ddd'    , 1, 1, '', '', 13),
        (14, 'USER'  , 0, 'ZZZ'  , 'zzz'    , 0, 1, '', '', 14),
        (15, 'ADMIN' , 0, 'Admin', 'admin'  , 1, 0, '', '', 1),
        (16, 'SYSTEM', 0, 'System','system' , 1, 0, '', '', 2),
        (17, 'USER'  , 0, 'User1', 'user1'  , 1, 0, 'Tran Van 1'  , 'tranvan1@yahoo.com', null),
        (18, 'USER'  , 0, 'User2', 'user2'  , 0, 0, 'Nguyen Thi 2', 'nguyenthi2@yahoo.com', null),
        (19, 'USER'  , 0, 'User3', 'user3'  , 1, 0, 'Truong Van 3', 'truongvan3@yahoo.com', null);


INSERT INTO ROLE (ID, VERSION, NAME)
    VALUES
        (1, 0, 'ROLE_USER'),
        (2, 0, 'ROLE_ADMIN'),
        (3, 0, 'ROLE_SYSTEM');


INSERT USER_ROLE (ID, USER_ID, ROLE_ID, EXPIRY_DATE)
    VALUES
        (1 , 1 , 1, '2020:01:01 01:01:01'),
        (2 , 1 , 2, '2020:02:02 02:02:02'),
        (3 , 2 , 1, '2020:03:03 03:03:03'),
        (4 , 2 , 3, '2020:04:04 04:04:04'),
        (5 , 3 , 1, '2020:05:05 05:05:05'),
        (6 , 4 , 1, '2020:06:06 06:06:06'),
        (7 , 5 , 1, '2020:07:07 07:07:07'),
        (8 , 6 , 1, '2020:08:08 08:08:08'),
        (9 , 7 , 1, '2020:09:09 09:09:09'),
        (10, 8 , 1, '2020:06:06 06:06:06'),
        (11, 9 , 1, '2020:06:06 06:06:06'),
        (12, 10, 2, '2020:06:06 06:06:06'),
        (13, 11, 1, '2020:06:06 06:06:06'),
        (14, 12, 3, '2020:06:06 06:06:06'),
        (15, 13, 1, '2020:06:06 06:06:06'),
        (16, 14, 1, '2020:06:06 06:06:06'),
        (17, 15, 2, '2020:06:06 06:06:06'),
        (18, 16, 2, '2020:06:06 06:06:06'),
        (19, 17, 1, '2020:06:06 06:06:06'),
        (20, 18, 1, '2020:06:06 06:06:06'),
        (21, 19, 3, '2020:06:06 06:06:06');

        
INSERT INTO COUNTRY (ID, VERSION, CODE, NAME)
    VALUES
        (1  , 0, 'AFG', 'Afghanistan'),
        (2  , 0, 'ALB', 'Albania'),
        (3  , 0, 'ALG', 'Algeria'),
        (4  , 0, 'AND', 'Andorra'),
        (5  , 0, 'ANG', 'Angola '),
        (6  , 0, 'ARG', 'Argentina'),
        (7  , 0, 'ARM', 'Armenia'),
        (8  , 0, 'ARU', 'Aruba'),
        (9  , 0, 'ASA', 'American Samoa'),
        (10 , 0, 'AUT', 'Australia'),
        (11 , 0, 'AZE', 'Azerbaijan'),
        (12 , 0, 'BAH', 'Bahamas'),
        (13 , 0, 'BAN', 'Bangladesh'),
        (14 , 0, 'BAR', 'Barbados'),
        (15 , 0, 'BDI', 'Burundi'),
        (16 , 0, 'BEL', 'Belgium'),
        (17 , 0, 'BEN', 'Benin'),
        (18 , 0, 'BER', 'Bermuda'),
        (19 , 0, 'BHU', 'Bhutan'),
        (20 , 0, 'BIH', 'Bosnia and Herzegovina'),
        (21 , 0, 'BIZ', 'Belize'),
        (22 , 0, 'BLR', 'Belarus'),
        (23 , 0, 'BOL', 'Bolivia'),
        (24 , 0, 'BOT', 'Botswana'),
        (25 , 0, 'BRA', 'Brazil'),
        (26 , 0, 'BRN', 'Bahrain'),
        (27 , 0, 'BRU', 'Brunei'),
        (28 , 0, 'BUL', 'Bulgaria'),
        (29 , 0, 'BUR', 'Burkina Faso'),
        (30 , 0, 'CAF', 'Central African Republic'),
        (31 , 0, 'CAM', 'Cambodia'),
        (32 , 0, 'CAN', 'Canada'),
        (33 , 0, 'CAY', 'Cayman Islands'),
        (34 , 0, 'CGO', 'Congo'),
        (35 , 0, 'CHA', 'Chad'),
        (36 , 0, 'CHI', 'Chile'),
        (37 , 0, 'CHN', 'China'),
        (38 , 0, 'CIV', 'Côte d"Ivoire'),
        (39 , 0, 'CMR', 'Cameroon'),
        (40 , 0, 'COD', 'DR Congo'),
        (41 , 0, 'COK', 'Cook Islands'),
        (42 , 0, 'COL', 'Colombia'),
        (43 , 0, 'COM', 'Comoros'),
        (44 , 0, 'CPV', 'Cape Verde'),
        (45 , 0, 'CRC', 'Costa Rica'),
        (46 , 0, 'CRO', 'Croatia'),
        (47 , 0, 'CUB', 'Cuba'),
        (48 , 0, 'CYP', 'Cyprus'),
        (49 , 0, 'CZE', 'Czech Republic'),
        (50 , 0, 'DAN', 'Denmark'),
        (51 , 0, 'DJI', 'Djibouti'),
        (52 , 0, 'DMA', 'Dominica'),
        (53 , 0, 'DOM', 'Dominican Republic'),
        (54 , 0, 'ECU', 'Ecuador'),
        (55 , 0, 'EGY', 'Egypt'),
        (56 , 0, 'ESA', 'El Salvador'),
        (57 , 0, 'ESP', 'Spain'),
        (58 , 0, 'ETS', 'Estonia'),
        (59 , 0, 'ETH', 'Ethiopia'),
        (60 , 0, 'FIJ', 'Fiji'),
        (61 , 0, 'FIN', 'Finland'),
        (62 , 0, 'FRA', 'France'),
        (63 , 0, 'GAB', 'Gabon'),
        (64 , 0, 'GAM', 'Gambia'),
        (65 , 0, 'GBR', 'Great Britain'),
        (66 , 0, 'GEO', 'Georgia'),
        (67 , 0, 'GER', 'Germany'),
        (68 , 0, 'GHA', 'Ghana'),
        (69 , 0, 'GRE', 'Greece'),
        (70 , 0, 'GUA', 'Guatemala'),
        (71 , 0, 'GUI', 'Guinea'),
        (72 , 0, 'GUM', 'Guam'),
        (73 , 0, 'HAI', 'Haiti'),
        (74 , 0, 'HKG', 'Hong Kong'),
        (75 , 0, 'HON', 'Honduras'),
        (76 , 0, 'HUN', 'Hungary'),
        (77 , 0, 'INA', 'Indonesia'),
        (78 , 0, 'IND', 'India'),
        (79 , 0, 'IRI', 'Iran'),
        (80 , 0, 'IRL', 'Ireland'),
        (81 , 0, 'IRQ', 'Iraq'),
        (82 , 0, 'ISL', 'Iceland'),
        (83 , 0, 'ISR', 'Israel'),
        (84 , 0, 'ITA', 'Italy'),
        (85 , 0, 'JAM', 'Jamaica'),
        (86 , 0, 'JOR', 'Jordan'),
        (87 , 0, 'JPN', 'Japan'),
        (88 , 0, 'KAZ', 'Kazakhstan'),
        (89 , 0, 'KEN', 'Kenya'),
        (90 , 0, 'KGZ', 'Kyrgyzstan'),
        (91 , 0, 'KOR', 'South Korea'),
        (92 , 0, 'KSA', 'Saudi Arabia'),
        (93 , 0, 'KUW', 'Kuwait'),
        (94 , 0, 'LAO', 'Laos'),
        (95 , 0, 'LAT', 'Latvia'),
        (96 , 0, 'LBA', 'Libya'),
        (97 , 0, 'LBR', 'Liberia'),
        (98 , 0, 'LIB', 'Lebanon'),
        (99 , 0, 'LTU', 'Lithuania'),
        (100, 0, 'LUX', 'Luxembourg'),
        (101, 0, 'MAD', 'Madagascar'),
        (102, 0, 'MAR', 'Morocco'),
        (103, 0, 'MAS', 'Malaysia'),
        (104, 0, 'MDA', 'Moldova'),
        (105, 0, 'MDV', 'Maldives'),
        (106, 0, 'MEX', 'Mexico'),
        (107, 0, 'MGL', 'Mongolia'),
        (108, 0, 'MLI', 'Mali'),
        (109, 0, 'MLT', 'Malta'),
        (110, 0, 'MNE', 'Montenegro'),
        (111, 0, 'MON', 'Monaco'),
        (112, 0, 'MOZ', 'Mozambique'),
        (113, 0, 'MYA', 'Myanmar'),
        (114, 0, 'NAM', 'Namibia'),
        (115, 0, 'NCA', 'Nicaragua'),
        (116, 0, 'NED', 'Netherlands'),
        (117, 0, 'NEP', 'Nepal'),
        (118, 0, 'NGR', 'Nigeria'),
        (119, 0, 'NOR', 'Norway'),
        (120, 0, 'NZL', 'New Zealand'),
        (121, 0, 'OMA', 'Oman'),
        (122, 0, 'PAK', 'Pakistan'),
        (123, 0, 'PAN', 'Panama'),
        (124, 0, 'PAR', 'Paraguay'),
        (125, 0, 'PER', 'Peru'),
        (126, 0, 'PHI', 'Philippines'),
        (127, 0, 'PLE', 'Palestine'),
        (128, 0, 'POL', 'Poland'),
        (129, 0, 'POR', 'Portugal'),
        (130, 0, 'PRK', 'North Korea'),
        (131, 0, 'PUR', 'Puerto Rico'),
        (132, 0, 'QAT', 'Qatar'),
        (133, 0, 'ROU', 'Romania'),
        (134, 0, 'RSA', 'South Africa'),
        (135, 0, 'RUS', 'Russia'),
        (136, 0, 'SEN', 'Senegal'),
        (137, 0, 'SIN', 'Singapore'),
        (138, 0, 'SLO', 'Slovenia'),
        (139, 0, 'SRB', 'Serbia'),
        (140, 0, 'SRI', 'Sri Lanka'),
        (141, 0, 'SUD', 'Sudan'),
        (142, 0, 'SUI', 'Switzerland'),
        (143, 0, 'SVK', 'Slovakia'),
        (144, 0, 'SWE', 'Sweden'),
        (145, 0, 'SYR', 'Syria'),
        (146, 0, 'THA', 'Thailand'),
        (147, 0, 'TOG', 'Togo'),
        (148, 0, 'TPE', 'Chinese Taipei'),
        (149, 0, 'TRI', 'Trinidad and Tobago'),
        (150, 0, 'TUN', 'Tunisia'),
        (151, 0, 'TUR', 'Turkey'),
        (152, 0, 'UGA', 'Uganda'),
        (153, 0, 'UKR', 'Ukraine'),
        (154, 0, 'URU', 'Uruguay'),
        (155, 0, 'USA', 'United States'),
        (156, 0, 'UZB', 'Uzbekistan'),
        (157, 0, 'VEN', 'Venezuela'),
        (158, 0, 'VIE', 'Vietnam'),
        (159, 0, 'YEM', 'Yemen'),
        (160, 0, 'ZIM', 'Zimbabwe');


INSERT INTO PROVINCE (ID, CODE, NAME, COUNTRY_ID)
    VALUES
        (1 , 'AB', 'Alberta', 32),
        (2 , 'BC', 'British Columbia', 32),
        (3 , 'MN', 'Manitoba', 32),
        (4 , 'NB', 'New Brunswick', 32),
        (5 , 'NF', 'Newfoundland and Labrador', 32),
        (6 , 'NT', 'Northwest Territories', 32),
        (7 , 'NS', 'Nova Scotia', 32),
        (8 , 'NU', 'Nunavut', 32),
        (9 , 'ON', 'Ontario', 32),
        (10, 'PE', 'Prince Edward Island', 32),
        (11, 'QC', 'Quebec', 32),
        (12, 'SK', 'Saskatchewan', 32),
        (13, 'YT', 'Yukon Territory', 32),
        (14, 'HCM', 'Ho Chi Minh', 158),
        (15, 'HNO', 'Ha Noi', 158);


INSERT INTO PARTY (ID, VERSION, EMAIL)
   VALUES
        (1 , 0, 'vuahoang66@gmail.com'),
        (2 , 0, 'tdhung80@gmail.com'),
        (3 , 0, 'viencareer@gmail.com'),
        (4 , 0, 'nhtvinh@gmail.com'),
        (5 , 0, 'thaoximuoi@yahoo.com'),
        (6 , 0, 'uyenvo80@yahoo.com'),
        (7 , 0, 'hongtramho@yahoo.com'),
        (8 , 0, 'info@ygsolution.org'),
        (9 , 0, 'info@nsbaynui.com'),
        (10, 0, 'info@aaa.com'),
        (11, 0, 'info@bbb.com'),
        (12, 0, 'info@ccc.com'),
        (13, 0, 'info@ddd.com'),
        (14, 0, 'info@zzz.com');

INSERT INTO PERSON (ID, FIRST_NAME, LAST_NAME, FULL_NAME, SEX, ETHNIC_GROUP,
                    HOME_STREET, HOME_CITY, HOME_PROVINCE_ID, HOME_COUNTRY_ID, HOME_PHONE, HOME_POSTAL_OR_ZIP_CODE,
                    WORK_STREET, WORK_CITY, WORK_PROVINCE_ID, WORK_COUNTRY_ID, WORK_PHONE, WORK_POSTAL_OR_ZIP_CODE)
   VALUES
        (1, 'Le Hoang', 'Truong', 'Truong, Le Hoang', 'Male', 'Asian',
            '66 Home Street' , 'Montreal', 11, 32, '666-666-6666', 'ABCDEF',
            '66 Work Street' , 'Montreal', 11, 32, '666-666-6666', 'TUVXYZ'),
        (2, 'Dinh Hung', 'Tran', 'Tran, Dinh Hung', 'Male', 'Asian',
            '22 Home Street', 'HCM', 14, 158, '222-222-2222', '222222',
            '22 Work Street', 'HCM', 14, 158, '222-222-2222', '222222'),
        (3, 'Huu Vien', 'Nguyen', 'Nguyen, Huu Vien', 'Male', 'Asian',
            '33 Home Street', 'HCM', 14, 158, '333-333-3333', '333333',
            '33 Work Street', 'HCM', 14, 158, '333-333-3333', '333333'),
        (4, 'Huu Tuong Vinh', 'Nguyen', 'Nguyen, Huu Tuong Vinh', 'Male', 'Asian',
            '44 Home Street', 'HCM', 14, 158, '444-444-4444', '444444',
            '44 Work Street', 'HCM', 14, 158, '444-444-4444', '444444'),
        (5, 'Thi Thu Thao', 'Than', 'Than, Thi Thu Thao', 'Female', 'Asian',
            '55 Home Street', 'HCM', 14, 158, '555-555-5555', '555555',
            '55 Work Street', 'HCM', 14, 158, '555-555-5555', '555555'),
        (6, 'Thi Cat Uyen', 'Vo', 'Vo, Thi Cat Uyen', 'Female', 'Asian',
            '66 Home Street', 'HCM', 14, 158, '666-666-6666', '666666',
            '66 Work Street', 'HCM', 14, 158, '666-666-6666', '666666'),
        (7, 'Thi Hong Tram', 'Ho', 'Ho, Thi Hong Tram', 'Female', 'Asian',
            '77 Home Street', 'HCM', 14, 158, '777-777-7777', '777777',
            '77 Work Street', 'HCM', 14, 158, '777-777-7777', '777777');


INSERT INTO ORGANIZATION (ID, NAME, STREET, CITY, PROVINCE_ID, COUNTRY_ID, PHONE, POSTAL_OR_ZIP_CODE)
   VALUES
        (8 , 'YGS' , '88 Street', 'HCM', 14, 158, '888-888-8888', '888888'),
        (9 , 'NSBN', '99 Street', 'HCM', 14, 158, '999-999-9999', '999999'),
        (10, 'AAA' , '00 Street', 'HCM', 14, 158, '000-000-0000', '000000'),
        (11, 'BBB' , '11 Street', 'HCM', 14, 158, '111-111-1111', '111111'),
        (12, 'CCC' , '12 Street', 'HCM', 14, 158, '222-222-2222', '222222'),
        (13, 'DDD' , '13 Street', 'HCM', 14, 158, '333-333-3333', '333333'),
        (14, 'ZZZ' , '14 Street', 'HCM', 14, 158, '444-444-4444', '444444');


INSERT INTO MAIL (ID, VERSION, HOST, USERNAME, PASSWORD, SENDER_HOST, SENDER_ADDRESS, SENDER_NAME, SUBJECT, TIME_INTERVAL)
   VALUES
        (1 , 0, 'http://mail.hoang.com', 'vuahoang66', 'vuahoang66', 'http://mail.hoang.com', 'vuahoang66@gmail.com', 'Hoang Truong', 'Hoang Mail Processing Sysem', 300);


INSERT INTO MAILER (ID, VERSION, NAME, MAIL_ID)
   VALUES
        (1 , 0, 'YGS', 1),
        (2 , 0, 'NSBN', 1);


INSERT INTO RECIPIENT (ID, VERSION, NAME, ADDRESS, MAILER_ID)
   VALUES
        (1 , 0, 'Hoang Truong', 'hoang.truong@ygsolution.org', 1),
        (2 , 0, 'Hung Tran', 'hung.tran@ygsolution.org', 1),
        (3 , 0, 'Vien Nguyen', 'vien.nguyen@ygsolution.org', 1),
        (4 , 0, 'Vinh Nguyen', 'vinh.nguyen@ygsolution.org', 1),
        (5 , 0, 'Vien Nguyen', 'vien.nguyen@ygsolution.org', 2);


INSERT INTO ITEM (ID, VERSION, NAME, DESCRIPTION, DATE_CREATED, DATE_MODIFIED, MAILER_ID)
   VALUES
        (1 , 0, 'Laptop', 'Fixed Asset', '06/06/2009', '09/09/2010', 1 );


INSERT INTO PLACE (ID, NAME, ACTIVITIES, ADDRESS, PHONE)
    VALUES
        (1 , 'Subway', 'Bread', 'N.A', 'no reservation needed'),
        (2 , 'Pho Hanoi', 'Vietnamese Food', '5557 a CH. Cote-Des-Neiges', '514-733-8989'),
        (3 , 'McDonald', 'Hamburger, French Fries', 'N.A', 'no reservation needed'),
        (4 , 'Pushap', 'Vegetarian Indian Food', '5195 Pare', '514-737-4527'),
        (5 , 'Burger King', 'Hamburger, Coffee', 'N.A', 'no reservation needed'),
        (6 , 'Souvlaki', 'Meat Stick, French Fries', 'Hamburger, Coffee', '514-731-6455'),
        (7 , 'Baton Rouge', 'Rib, Hamburger', '5385, des Jockeys', '514-738-1616'),
        (8 , 'Hoai Huong', 'Soup Tonkinoise, Noodle, Rice', '5485 Avenue Victoria', '514-738-6610'),
        (9 , 'Tim Horton', 'Cake, Coffee', 'N.A', 'no reservation needed'),
        (10, 'Orange', 'Orange Juice, Tuna Sandwitch', 'Rue Pare', 'no reservation needed'),
        (11, 'Harvey', 'Sandwitch', 'N.A', 'no reseravation needed'),
        (12, 'Amir', 'Lebanese Food', '5252 RUE JEAN-TALON OUEST', '514-739-2647'),
        (13, 'Hot and spicy', 'Chinese Food', '7373 Boulevard Decarie', '514-731-1818'),
        (14, 'Atami', 'Cuisine japonaise (sushi bar)', '5499 Cote-des-Neiges', '514-735-5400'),
        (15, 'Kanda', 'Sushi, Maki', '5240 Queen Mary', '514-483-6388'),
        (16, 'Pare', 'Delicatessen', 'up Pare St.', '000-000-0000');


SET FOREIGN_KEY_CHECKS = 1;
COMMIT;
