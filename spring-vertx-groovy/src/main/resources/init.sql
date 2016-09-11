create schema templates;

CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `age` int(11) DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO contact(`id`,  `firstname`, `lastname`, `age`,  birthdate) values (1,  'Andreas', 'Loibl', 64,  '2011-12-18 00:00:00');