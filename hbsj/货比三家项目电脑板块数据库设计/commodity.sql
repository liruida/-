/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : cp
Target Host     : localhost:3306
Target Database : cp
Date: 2018-04-11 14:22:15
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(255) DEFAULT NULL,
  `comments` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `Hyperlinks` char(255) DEFAULT NULL,
  `imgadress` char(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commodity
-- ----------------------------
