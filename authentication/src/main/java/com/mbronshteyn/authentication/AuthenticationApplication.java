package com.mbronshteyn.authentication;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mbronshteyn.authentication.resources.AuthenticationResource;
import com.mbronshteyn.authentication.resources.UserResource;
import com.mbronshteyn.authentication.security.binding.AuthinticationDynamicFeature;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableEncryptableProperties
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@ComponentScan({"com.mbronshteyn.data","com.mbronshteyn.gameserver.services","com.mbronshteyn.authentication"})
@EnableJpaRepositories(basePackages="com.mbronshteyn.data")
@EntityScan(basePackages="com.mbronshteyn.data")
public class AuthenticationApplication extends ResourceConfig{
    
	public AuthenticationApplication() {

		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);

        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        
        register(AuthenticationResource.class); 
        register(UserResource.class);  
                
        register(RolesAllowedDynamicFeature.class);         
		register(AuthinticationDynamicFeature.class);  
		
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
