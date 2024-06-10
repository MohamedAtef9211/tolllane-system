package com.egabi.tcs;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Set;

@SpringBootApplication
public class TollaneUISpringApplication {

    public static void main(String[] args) {
        Application.launch(TollaneJavaFxApplication.class, args);
    }

	@Bean
	public ServletWebServerFactory servletWebServerFactory(){
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(8080);
        System.err.println("Context Path " + factory.getContextPath());
		return factory;
	}

//    @Bean
//    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//        return new RequestMappingHandlerMapping();
//    }
//
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                applicationContext.getBean(RequestMappingHandlerMapping.class);

        requestMappingHandlerMapping.getHandlerMethods()
                .forEach((requestMappingInfo, handlerMethod) -> {
                    Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
                    Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();

                    methods.forEach(method ->
                            patterns.forEach(pattern ->
                                    System.err.println("Method: " + method + ", Path: " + pattern + ", Handler: " + handlerMethod)
                            )
                    );
                });
    }
}
