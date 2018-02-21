package com.mbronshteyn.gameserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mbronshteyn.gameserver.resources.DistributorResource;

import io.swagger.jaxrs.config.BeanConfig;

@Configuration
public class SwaggerConfig {
	
	@Value("${server.context-path:/}")
	private String apiPath;
	  
    @Bean
    public BeanConfig swaggerConfiguration() {
        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage(DistributorResource.class.getPackage().getName());  
        beanConfig.setTitle("Gaming Engine Backend API");
        beanConfig.setVersion("v1");  
        beanConfig.setSchemes(new String[] { "http", "https" });
        beanConfig.setBasePath(apiPath);        
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);        
        return beanConfig;
    }	

}
