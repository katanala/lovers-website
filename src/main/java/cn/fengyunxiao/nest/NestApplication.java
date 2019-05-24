package cn.fengyunxiao.nest;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class NestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NestApplication.class);
    }

    // application.yml 配置了 ssl 后，默认使用 https，加入以下配置，同时支持 http
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(80);
        tomcat.addAdditionalTomcatConnectors(connector); // 添加http
        return tomcat;
    }

    // 该配置激活 http的ws 和 https的wss，支持 websockt 聊天
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

