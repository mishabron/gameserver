package com.mbronshteyn.gameserver;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mbronshteyn.gameserver.resources.DistributorResource;

@SpringBootApplication
@EnableJpaAuditing
public class GameEngineApplication extends ResourceConfig{

		
	public GameEngineApplication() {		
		
        //packages(this.getClass().getPackage().getName());
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		
		//REST API Resources
		register(DistributorResource.class);        

	}

	public static void main(String[] args) {
		SpringApplication.run(GameEngineApplication.class, args);
	}
}
