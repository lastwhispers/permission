/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库-mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : permission

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 03/05/2019 13:10:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operateor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operateType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operateDate` datetime(0) NULL DEFAULT NULL,
  `operateResult` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '编号',
  `menuname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应URL',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标样式',
  `pid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上一级菜单编号',
  `is_parent` tinyint(1) NULL DEFAULT NULL COMMENT '该菜单是否为父菜单，1为true，0为false',
  PRIMARY KEY (`menuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0', '系统菜单', '-', 'icon-sys', '-1', 1);
INSERT INTO `menu` VALUES ('100', '系统管理', '-', 'icon-sys', '0', 1);
INSERT INTO `menu` VALUES ('101', '用户管理', 'user.html', 'icon-log', '100', 0);
INSERT INTO `menu` VALUES ('102', '角色管理', 'role.html', 'icon-log', '100', 0);
INSERT INTO `menu` VALUES ('103', '菜单管理', 'menu.html', 'icon-log', '100', 1);
INSERT INTO `menu` VALUES ('104', '角色权限管理', 'roleMenuSet.html', 'icon-log', '100', 0);
INSERT INTO `menu` VALUES ('105', '用户角色管理', 'userRoleSet.html', 'icon-log', '100', 0);
INSERT INTO `menu` VALUES ('106', '日志管理', 'log.html', 'icon-log', '100', 1);
INSERT INTO `menu` VALUES ('107', '数据库监控', 'druid/index.html', 'icon-log', '100', 0);
INSERT INTO `menu` VALUES ('200', '人事管理', '-', 'icon-sys', '0', 1);
INSERT INTO `menu` VALUES ('201', '部门', 'dept.html', 'icon-log', '200', 0);
INSERT INTO `menu` VALUES ('202', '员工', 'emp.html', 'icon-log', '200', 0);
INSERT INTO `menu` VALUES ('300', '销售管理', '-', 'icon-sys', '0', 1);
INSERT INTO `menu` VALUES ('301', '销售额统计', 'sale.html', 'icon-log', '300', 0);
INSERT INTO `menu` VALUES ('400', '临时工管理', '-', 'icon-sys', '0', 1);
INSERT INTO `menu` VALUES ('401', '临时工计件', 'casual.html', 'icon-log', '400', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `uuid` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员');
INSERT INTO `role` VALUES (2, '经理');
INSERT INTO `role` VALUES (3, '销售主管');
INSERT INTO `role` VALUES (4, '销售员');
INSERT INTO `role` VALUES (5, '临时工');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `menuuuid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
  `roleuuid` int(10) NULL DEFAULT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('100', 1);
INSERT INTO `role_menu` VALUES ('101', 1);
INSERT INTO `role_menu` VALUES ('102', 1);
INSERT INTO `role_menu` VALUES ('103', 1);
INSERT INTO `role_menu` VALUES ('104', 1);
INSERT INTO `role_menu` VALUES ('105', 1);
INSERT INTO `role_menu` VALUES ('106', 1);
INSERT INTO `role_menu` VALUES ('107', 1);
INSERT INTO `role_menu` VALUES ('200', 2);
INSERT INTO `role_menu` VALUES ('201', 2);
INSERT INTO `role_menu` VALUES ('202', 2);
INSERT INTO `role_menu` VALUES ('300', 3);
INSERT INTO `role_menu` VALUES ('301', 3);
INSERT INTO `role_menu` VALUES ('300', 4);
INSERT INTO `role_menu` VALUES ('301', 4);
INSERT INTO `role_menu` VALUES ('400', 5);
INSERT INTO `role_menu` VALUES ('401', 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆名',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `user_birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 269 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (221, 'admin', '我是系统管理员', '3ef7164d1f6167cb9f2658c07d3c2f0a', '2019-04-04');
INSERT INTO `user` VALUES (262, 'manager', '我是经理', 'bb2ec153e560c6cb94a35b370eb7a07b', '2019-05-02');
INSERT INTO `user` VALUES (263, 'salemanager', '我是销售主管', '40fd0e9ba2e7241cb04c49a7663464c1', '2019-05-06');
INSERT INTO `user` VALUES (264, 'saler', '我是销售员', '846366204c8cc44abdedf5f201da0b00', '2019-05-20');
INSERT INTO `user` VALUES (265, 'casual', '我是临时工', '130131080b677e15df326f8114c426a3', '2019-05-04');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `userid` int(11) NULL DEFAULT NULL COMMENT '员工编号',
  `roleuuid` int(11) NULL DEFAULT NULL COMMENT '角色编号'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (265, 5);
INSERT INTO `user_role` VALUES (264, 4);
INSERT INTO `user_role` VALUES (263, 3);
INSERT INTO `user_role` VALUES (262, 2);
INSERT INTO `user_role` VALUES (221, 1);
INSERT INTO `user_role` VALUES (221, 5);

SET FOREIGN_KEY_CHECKS = 1;
