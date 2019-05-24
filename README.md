### 项目说明

参考地址（address）：http://www.fengyunxiao.cn

备用地址（address）：https://www.fengyunxiao.cn


目标（goal）：部署简单，安全高效，只需修改配置，即可搭建自己的情侣网站，属于你们的小窝。


语言（language） ：java（jdk1.8）


数据库（database）：mysql 8


框架（framework）： SpringBoot2，SpringMVC，Mybatis，Freemarker，Websocket。


集成开发环境（IDE）：IDEA


模块（modular）：聊天（chat），博客（blog），留言（message），相册（album），故事（story），ip 统计（ip statistics），邮件通知（mail notification）....

---

### 部署说明

数据库sql：src\main\java\cn\fengyunxiao\nest\config\zscfyx.sql


密码、密钥等配置：src\main\java\cn\fengyunxiao\nest\config\ConfigI.java


数据库、邮箱等配置：src\main\resources\application.yml

---

### 运行说明


问：如何运行？

答：运行前请确保配置文件和数据库配置正确，运行入口类中的main方法，src\main\java\cn\fengyunxiao\nest\NestApplication.java


问：如何访问？

答：默认以https方式访问，浏览器输入地址：https://127.0.0.1


问：如何使用自己的域名SSL证书？

答：需要将 src\main\resources\www.fengyunxiao.cn.jks 换成自己域名的SSL证书，
同时修改 src\main\resources\application.yml 中的 server: ssl: key-* 的参数值

