package com.mbronshteyn.gameserver;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mbronshteyn.authentication.security.binding.AuthinticationDynamicFeature;
import com.mbronshteyn.gameserver.resources.DistributorResource;
import com.mbronshteyn.gameserver.resources.HeartBeatResource;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableEncryptableProperties
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
public class GameEngineApplication extends ResourceConfig{

		
	public GameEngineApplication() {		
		
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
		
        //packages(this.getClass().getPackage().getName());
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		
		//REST API Resources
		register(DistributorResource.class); 
		register(HeartBeatResource.class); 	
		
        register(RolesAllowedDynamicFeature.class);         
		register(AuthinticationDynamicFeature.class);  		

	}

	public static void main(String[] args) {
		SpringApplication.run(GameEngineApplication.class, args);
	}
}
