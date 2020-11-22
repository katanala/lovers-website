package cn.fengyunxiao.nest;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class NestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NestApplication.class, args);
    }

    // application.yml 配置了 ssl 后，默认使用 https，加入以下配置，同时支持 http
    @Bean
    public ServletWebServerFactory servletContainer() {
        // 不加 //{} 里面的内容，http 和 https 可以同时使用。加了该内容，从 http 强制跳到 https
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(80);
        connector.setRedirectPort(443);
        tomcat.addAdditionalTomcatConnectors(connector); // 添加http
        return tomcat;
    }

    // 该配置激活 http的ws 和 https的wss，支持 websockt 聊天
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
