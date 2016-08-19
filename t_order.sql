/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : pay_server

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-08-19 16:32:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `orderId` bigint(100) NOT NULL DEFAULT '0' COMMENT '订单id',
  `sdkChannel` int(11) DEFAULT '0' COMMENT 'SDK渠道',
  `channelId` varchar(100) NOT NULL COMMENT 'sdk返回的渠道',
  `serverId` varchar(100) NOT NULL COMMENT ' 服ID',
  `userId` varchar(100) NOT NULL,
  `roleId` varchar(100) NOT NULL,
  `productId` varchar(100) NOT NULL DEFAULT '' COMMENT '产品ID',
  `productAmount` int(11) NOT NULL DEFAULT '0' COMMENT '产品金额',
  `productNum` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `virtualCurrency` int(11) NOT NULL DEFAULT '0' COMMENT '产品虚拟金额',
  `orderInfo` varchar(255) DEFAULT '' COMMENT '订单详情',
  `orderExt` varchar(255) DEFAULT '' COMMENT '扩展字段',
  `orderState` int(11) NOT NULL DEFAULT '0' COMMENT '订单验证状态 1成功0失败',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  `dispatchState` int(11) NOT NULL DEFAULT '0' COMMENT '发货状态 2失败 1成功 0准备发送',
  `dispatchTime` bigint(20) DEFAULT '0' COMMENT '发货成功时间',
  `dispatchCount` int(11) DEFAULT '0' COMMENT '发货次数',
  `nextDispatchTime` bigint(20) DEFAULT '0' COMMENT '下一次的发货时间',
  `paydes` varchar(255) DEFAULT '' COMMENT '充值描述',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
