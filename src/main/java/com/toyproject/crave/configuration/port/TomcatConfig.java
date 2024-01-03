//package com.toyproject.crave.configuration.port;
//
//import org.apache.catalina.connector.Connector;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TomcatConfig {
//
//    @Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//
//        // Add multiple connectors for different ports
//        for (int port = 17001; port <= 17100; ++port){
//            tomcat.addAdditionalTomcatConnectors(createConnector(port));
//        }
//        return tomcat;
//    }
//
//    private Connector createConnector(int port) {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(port);
//        return connector;
//    }
//}