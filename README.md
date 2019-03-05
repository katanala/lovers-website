参考地址（address）：http://www.fengyunxiao.cn

备用地址（address）：http://t.cn/Ef6v2Uw


目标（goal）：部署简单，安全高效，只需修改配置，即可搭建自己的情侣网站，属于你们的小窝。


语言（language） ：java（jdk1.8）


数据库（database）：mysql 8


框架（framework）： SpringBoot2，SpringMVC，Mybatis，Freemarker，Websocket。


模块（modular）：聊天（chat），博客（blog），留言（message），相册（album），故事（story），ip 统计（ip statistics），邮件通知（mail notification）....


---

### 部署说明

数据库sql：

src\main\java\cn\fengyunxiao\nest\config\zscfyx.sql


将代码拉取到本地后，需要修改以下配置文件：

src\main\java\cn\fengyunxiao\nest\config\ConfigI.java

（包含后台帐号密码、API密钥等配置）


src\main\resources\application.yml

（包含数据库和邮箱等配置）


### 运行说明


运行入口类中的main方法即可：

src\main\java\cn\fengyunxiao\nest\NestApplication.java


默认以https方式，浏览器打开：

https://127.0.0.1


如果使用自己的域名证书，需要将以下文件换成自己的，并且修改 application.yml 中的证书密码

src\main\resources\www.fengyunxiao.cn.jks
