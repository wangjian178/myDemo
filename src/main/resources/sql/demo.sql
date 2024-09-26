/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50631
 Source Host           : 127.0.0.1:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50631
 File Encoding         : 65001

 Date: 28/04/2024 15:57:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_language
-- ----------------------------
DROP TABLE IF EXISTS `SYS_LANGUAGE_MESSAGE`;
CREATE TABLE `SYS_LANGUAGE_MESSAGE`  (
                               `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `LANGUAGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '语言',
                               `CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '键',
                               `LABEL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
                               `CREATED_BY` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                               `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `UPDATED_BY` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
                               `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               `REMARK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                               `DELETED` int(1) NULL DEFAULT NULL COMMENT '删除标识0否1是',
                               PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
