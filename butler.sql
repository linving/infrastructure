/*
Navicat MySQL Data Transfer

Source Server         : Develop
Source Server Version : 50552
Source Host           : company.99kst.com:3306
Source Database       : butler

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2016-11-03 16:11:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for method_serource
-- ----------------------------
DROP TABLE IF EXISTS `method_serource`;
CREATE TABLE `method_serource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(128) NOT NULL COMMENT '方法对应程序的别名',
  `remark` varchar(128) DEFAULT NULL COMMENT '方法描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `value_UNIQUE` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='方法资源表';

-- ----------------------------
-- Records of method_serource
-- ----------------------------
INSERT INTO `method_serource` VALUES ('1', 'user:login', '登录');
INSERT INTO `method_serource` VALUES ('2', 'user:insert', '新增用户');
INSERT INTO `method_serource` VALUES ('3', 'user:list', '查询列表');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(128) DEFAULT NULL COMMENT '权限名',
  `remark` text COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '登录注册', '基本游客的功能');
INSERT INTO `permission` VALUES ('2', '查询列表', '普通会员的功能');

-- ----------------------------
-- Table structure for permission_method_serource
-- ----------------------------
DROP TABLE IF EXISTS `permission_method_serource`;
CREATE TABLE `permission_method_serource` (
  `permission_id` int(11) NOT NULL,
  `method_serource_id` int(11) NOT NULL,
  PRIMARY KEY (`permission_id`,`method_serource_id`),
  KEY `fk_permission_method_serource_method_serource1_idx` (`method_serource_id`),
  KEY `fk_permission_method_serource_permission1_idx` (`permission_id`),
  CONSTRAINT `fk_permission_method_serource_permission1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_permission_method_serource_method_serource1` FOREIGN KEY (`method_serource_id`) REFERENCES `method_serource` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission_method_serource
-- ----------------------------
INSERT INTO `permission_method_serource` VALUES ('1', '1');
INSERT INTO `permission_method_serource` VALUES ('2', '2');
INSERT INTO `permission_method_serource` VALUES ('2', '3');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(128) NOT NULL COMMENT '角色名',
  `remark` text COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'visitors', '游客权限');
INSERT INTO `role` VALUES ('2', 'member', '普通会员权限');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `fk_role_permission_permission1_idx` (`permission_id`),
  KEY `fk_role_permission_role1_idx` (`role_id`),
  CONSTRAINT `fk_role_permission_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_permission_permission1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('2', '2');

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `fk_role_user_user1_idx` (`user_id`),
  KEY `fk_role_user_role_idx` (`role_id`),
  CONSTRAINT `fk_role_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_user_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES ('2', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '用户密码',
  `phone` varchar(32) NOT NULL COMMENT '用户手机号码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '刘岗强', '123456', '17828164037');
INSERT INTO `user` VALUES ('2', '袁霖', '123456', '17828164036');
INSERT INTO `user` VALUES ('4', '张三', '123456', '17828164035');
