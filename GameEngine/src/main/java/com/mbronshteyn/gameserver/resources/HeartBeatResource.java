package com.mbronshteyn.gameserver.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Component
@Path("/")	
public class HeartBeatResource {
	
	@GET
    @Path("/hello")	
    @Produces("text/html")
    public String sayHello() {
        return "Hello Game Server";
    }	

}
