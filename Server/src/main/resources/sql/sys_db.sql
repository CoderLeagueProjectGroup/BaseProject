/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : sys_db

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 22/10/2018 23:59:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  `url` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作url',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` int(10) UNSIGNED NOT NULL COMMENT '创建人id',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `modify_id` int(11) NOT NULL COMMENT '修改人id',
  `delete_flag` tinyint(4) NOT NULL COMMENT '是否逻辑 删除，0否（默认），1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` int(10) UNSIGNED NOT NULL COMMENT '父及部门id，无则为0',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` int(11) NOT NULL COMMENT '创建 人id',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `modify_id` int(11) NOT NULL COMMENT '修改人id',
  `delete_flag` tinyint(4) NOT NULL COMMENT '是否逻辑删除，0否（默认），1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能模块名称',
  `parent_id` int(10) UNSIGNED NOT NULL COMMENT '父 模块id，没有的话为0',
  `order_num` int(11) NOT NULL COMMENT '排序序号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` int(10) UNSIGNED NOT NULL COMMENT '创建人id',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `modify_id` int(10) UNSIGNED NOT NULL COMMENT '修改人id',
  `delete_flag` tinyint(4) NULL DEFAULT NULL COMMENT '是否逻辑删除，0否(默认),1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for module_action
-- ----------------------------
DROP TABLE IF EXISTS `module_action`;
CREATE TABLE `module_action`  (
  `id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL COMMENT '模块id',
  `action_id` int(11) NOT NULL COMMENT '操作id',
  `main` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否主操作，0否，1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` int(11) NOT NULL COMMENT '创建人id',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  `modify_id` int(11) NOT NULL COMMENT '修改人id',
  `delete_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否逻辑删除，0否（默认），1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_module
-- ----------------------------
DROP TABLE IF EXISTS `role_module`;
CREATE TABLE `role_module`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` int(10) UNSIGNED NOT NULL COMMENT '角色id',
  `module_id` int(10) UNSIGNED NOT NULL COMMENT '功能模块id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
  `role_id` int(10) UNSIGNED NOT NULL COMMENT '角色id',
  `department_id` int(11) NOT NULL COMMENT '部门id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_id` int(10) UNSIGNED NOT NULL COMMENT '创建id',
  `modify_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `modify_id` int(11) NOT NULL COMMENT '修改人id',
  `delete_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 ，0：否（默认），1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
