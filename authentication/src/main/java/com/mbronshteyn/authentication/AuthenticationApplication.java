package com.mbronshteyn.authentication;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mbronshteyn.authentication.resources.AuthenticationResource;
import com.mbronshteyn.authentication.resources.UserResource;

@SpringBootApplication
@EnableJpaAuditing
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
public class AuthenticationApplication extends ResourceConfig{

	public AuthenticationApplication() {
        //packages(this.getClass().getPackage().getName());
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        
        register(AuthenticationResource.class); 
        register(UserResource.class);           
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
