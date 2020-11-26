### 项目说明


http 地址（address of http ）：http://www.fengyunxiao.cn

https地址（address of https）：https://www.fengyunxiao.cn


目标（goal）：部署简单，安全高效，只需修改配置，即可搭建自己的情侣网站，属于你们的小窝。

语言（language） ：java（jdk11）

数据库（database）：mysql 8

缓存技术（cache redis）：redis5

框架技术（framework）： SpringBoot2.3，Mybatis，Freemarker，Websocket

分布式存储：阿里云 OSS

集成开发环境（IDE）：IntelliJ IDEA

模块（modular）：聊天（chat），博客（blog），留言（message），相册（album），故事（story），ip 统计（ip statistics），邮件通知（mail notification）....

---

### 捐赠（donation）

| name  | address  |
-:|:-
| btc  | 19ipzAnQ2ZebbLukbpaebgSukXbK3FkicS |
| eth  | 0x99b896244e6f9dc4d49b4a1dce6a3971c40310f8 |
| ltc  | LYEwf8TvMikNdZEbZVbPmZ11yAydffoDdX |

---

### 部署说明

数据库：nest.sql，同时修改 application.yml 中的配置

redis：安装好以后，同时修改 application.yml 中的配置

密码、密钥等配置：nest.sql 中的 install 表

数据库、邮箱等配置：application.yml

---

### 运行说明


问：如何运行？

答：运行前请确保配置文件和数据库配置正确，运行入口类中的main方法，src\main\java\cn\fengyunxiao\nest\NestApplication.java


问：如何访问？

答：默认以http方式访问，浏览器输入地址：http://127.0.0.1


问：如何使用自己的域名SSL证书？

答：需要将 www.fengyunxiao.cn.jks 换成自己域名的SSL证书，
同时修改 application.yml 中的 server 部分

### 联系

> 扫码关注微信公众号联系我

![扫码关注微信公众号联系我](https://fengyunxiao.oss-cn-shanghai.aliyuncs.com/nest/photos/gongzhonghao.jpg)