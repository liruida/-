/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : cp
Target Host     : localhost:3306
Target Database : cp
Date: 2018-04-11 14:22:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `businesses` char(255) DEFAULT NULL,
  `rating` char(255) DEFAULT NULL,
  `brand` char(255) DEFAULT NULL,
  `markettime` char(255) DEFAULT NULL,
  `screen size` char(255) DEFAULT NULL,
  `cpu` char(255) DEFAULT NULL,
  `producttype` char(255) DEFAULT NULL,
  `graphics` char(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
