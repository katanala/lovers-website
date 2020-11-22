/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : nest

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 22/11/2020 17:44:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `bid` int(0) NOT NULL AUTO_INCREMENT,
  `rank` tinyint(0) NOT NULL DEFAULT 0 COMMENT '2推荐1普通-1草稿',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `keyword` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `modtime` datetime(0) NULL DEFAULT NULL,
  `url` varchar(900) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`bid`) USING BTREE,
  UNIQUE INDEX `blog_bid`(`bid`) USING BTREE,
  FULLTEXT INDEX `blog_search`(`title`, `keyword`) WITH PARSER `ngram`
) ENGINE = InnoDB AUTO_INCREMENT = 403 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `cid` int(0) NOT NULL AUTO_INCREMENT,
  `male` tinyint(1) NOT NULL,
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pubtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  UNIQUE INDEX `chat_cid`(`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4456 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `iid` int(0) NOT NULL AUTO_INCREMENT,
  `bid` int(0) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pubtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`iid`) USING BTREE,
  UNIQUE INDEX `image_iid`(`iid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for install
-- ----------------------------
DROP TABLE IF EXISTS `install`;
CREATE TABLE `install`  (
  `key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(900) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `intro` varchar(900) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  UNIQUE INDEX `install_key`(`key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of install
-- ----------------------------
INSERT INTO `install` VALUES ('adminGoogle', 'ZVZBZZYZCZZORZ4Z', '管理者谷歌验证码');
INSERT INTO `install` VALUES ('adminLoginToken', '123456', '管理者登录token');
INSERT INTO `install` VALUES ('adminMail', '123456@qq.com', '邮件接收者');
INSERT INTO `install` VALUES ('adminPhone', '123456', '管理者账号');
INSERT INTO `install` VALUES ('adminToken', '123456', '管理者token');
INSERT INTO `install` VALUES ('apiLiliKey', '123456', '机器人 丽丽 key');
INSERT INTO `install` VALUES ('apiLiliSecret', '123456', '机器人 丽丽 Secret');
INSERT INTO `install` VALUES ('apiTulingKey', '123456', '机器人 图灵 key');
INSERT INTO `install` VALUES ('cdnBootstrapCss', 'https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css', 'cdn boot css');
INSERT INTO `install` VALUES ('cdnBootstrapJs', 'https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js', 'cdn boot js');
INSERT INTO `install` VALUES ('cdnChartJs', 'https://lib.baomitu.com/Chart.js/2.9.3/Chart.min.js', 'cdnChartJs');
INSERT INTO `install` VALUES ('cdnJqueryJs', 'https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js', 'cdn jquery js');
INSERT INTO `install` VALUES ('cdnLazyloadJs', 'https://cdn.bootcss.com/jquery_lazyload/1.9.7/jquery.lazyload.min.js', 'cdnLazyloadJs');
INSERT INTO `install` VALUES ('cdnMuiCss', 'https://lib.baomitu.com/mui/3.7.1/css/mui.min.css', 'cdn mui css');
INSERT INTO `install` VALUES ('cdnMuiJs', 'https://lib.baomitu.com/mui/3.7.1/js/mui.min.js', 'cdn mui js');
INSERT INTO `install` VALUES ('cdnXssJs', 'https://cdn.jsdelivr.net/npm/xss@1.0.6/dist/xss.min.js', 'cdn xss js');
INSERT INTO `install` VALUES ('firstAdminMessage', '欢迎访问，欢迎观看，欢迎留言', '首页 站长留言');
INSERT INTO `install` VALUES ('firstContactQQ', '2736629976', '首页 联系QQ');
INSERT INTO `install` VALUES ('firstCopyRight', 'CopyRight © 2015-', '首页 版权');
INSERT INTO `install` VALUES ('firstKeywords', '我们的小窝,冯芸霄,张圣晨', '首页 关键字');
INSERT INTO `install` VALUES ('firstLoveDay', '11', '首页 相爱日期');
INSERT INTO `install` VALUES ('firstLoveMonth', '11', '首页 相爱月份');
INSERT INTO `install` VALUES ('firstLoveYear', '2015', '首页 相爱年份');
INSERT INTO `install` VALUES ('firstTimeLove', '2015-11-11', '首页 相爱时间');
INSERT INTO `install` VALUES ('firstTimeStampHtml', '1', '首页 缓存版本');
INSERT INTO `install` VALUES ('firstTitlePrimary', 'Our Small Nest', '首页 标题');
INSERT INTO `install` VALUES ('firstTitleSecondary', '晨哥 &nbsp; ❤ &nbsp; 芸霄', '首页 副标题');
INSERT INTO `install` VALUES ('imageLocalPath', '/home/image/', '图片保存的本地位置');
INSERT INTO `install` VALUES ('imageQuality', '0.5f', '图片压缩品质');
INSERT INTO `install` VALUES ('imageSizeMax', '600', '图片最大尺寸');
INSERT INTO `install` VALUES ('imageSuffix', '.jpg,.png,.jpeg,.png,.gif', '图片支持类型');
INSERT INTO `install` VALUES ('ipCount', '0', '记录总的ip个数');
INSERT INTO `install` VALUES ('ipIgnore', '0:0:0:0:0:0:0:0 0:0:0:0:0:0:0:1 127.0.0.1 0.0.0.0', '不记录访问的 ip');
INSERT INTO `install` VALUES ('letterMaxNum', '3', '每天每个ip最多留言几次');
INSERT INTO `install` VALUES ('ossAccessKeyId', '123456', 'oss key id');
INSERT INTO `install` VALUES ('ossAccessKeySecret', '123456', 'oss key secret');
INSERT INTO `install` VALUES ('ossBlogFolder', 'nest/blog/', 'oss 博客路径');
INSERT INTO `install` VALUES ('ossBucket', 'fengyunxiao', 'oss包名');
INSERT INTO `install` VALUES ('ossEndpoint', 'oss-cn-shanghai.aliyuncs.com', 'oss端点');
INSERT INTO `install` VALUES ('ossPhotoFolder', 'nest/', 'oss 图片路径');
INSERT INTO `install` VALUES ('ossProtocol', 'http://', '');
INSERT INTO `install` VALUES ('ossUrlPrefix', 'https://static.fengyunxiao.cn/', 'oss 访问 url');
INSERT INTO `install` VALUES ('pageNumber', '10', '分页时，每页数量');

-- ----------------------------
-- Table structure for ip
-- ----------------------------
DROP TABLE IF EXISTS `ip`;
CREATE TABLE `ip`  (
  `iid` int(0) NOT NULL AUTO_INCREMENT,
  `ip` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `isp` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `curtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`iid`) USING BTREE,
  UNIQUE INDEX `ip_iid`(`iid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35068 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for letter
-- ----------------------------
DROP TABLE IF EXISTS `letter`;
CREATE TABLE `letter`  (
  `lid` int(0) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pubtime` datetime(0) NULL DEFAULT NULL,
  `zan` int(0) NOT NULL DEFAULT 0,
  `ip` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`lid`) USING BTREE,
  UNIQUE INDEX `letter_lid`(`lid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 590 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `href` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `rank` int(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES ('木子博客', 'https://muuzi.cn', 1);
INSERT INTO `link` VALUES ('Hello萝莉', 'https://hellololi.com/', 1);
INSERT INTO `link` VALUES ('木朵爱', 'http://www.muduoai.cn', 1);
INSERT INTO `link` VALUES ('两个有意思的', 'https://dxq520.cn', 1);
INSERT INTO `link` VALUES ('纪实小屋', 'https://love109.cn', 1);

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `replyid` int(0) NOT NULL AUTO_INCREMENT,
  `lid` int(0) NOT NULL,
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `curtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`replyid`) USING BTREE,
  INDEX `reply_lid`(`lid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for timeline
-- ----------------------------
DROP TABLE IF EXISTS `timeline`;
CREATE TABLE `timeline`  (
  `lineid` int(0) NOT NULL AUTO_INCREMENT,
  `year` int(0) NULL DEFAULT 2000,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `content1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `content2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `content3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  PRIMARY KEY (`lineid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
