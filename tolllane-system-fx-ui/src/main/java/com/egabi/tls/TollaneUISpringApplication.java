package com.egabi.tls;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class TollaneUISpringApplication {

    public static void main(String[] args) {
        Application.launch(TollaneJavaFxApplication.class, args);
    }

	@Bean
	public ServletWebServerFactory servletWebServerFactory(){
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(8080);
		return factory;
	}

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        applicationContext.getBean(RequestMappingHandlerMapping.class)
                .getHandlerMethods().forEach((key, value) -> System.err.println("key is " + value));
    }

}
