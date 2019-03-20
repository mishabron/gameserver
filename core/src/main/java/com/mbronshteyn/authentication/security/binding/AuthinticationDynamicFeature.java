package com.mbronshteyn.authentication.security.binding;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.mbronshteyn.gameserver.filter.UserFilter;
import org.glassfish.jersey.server.model.AnnotatedMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mbronshteyn.authentication.security.filters.AuthenticationFilter;

@Configuration
@Provider
public class AuthinticationDynamicFeature implements DynamicFeature {
	
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {

	    //set authintication/authorization binding
        RolesAllowed ca = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);       		
        RolesAllowed ra = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class);
        
        if (ca != null || ra != null) {
        	context.register(AuthenticationFilter.class);
        }	

        //set user binding
        UserNameBinding ubc = resourceInfo.getResourceClass().getAnnotation(UserNameBinding.class);
        UserNameBinding ubm = resourceInfo.getResourceMethod().getAnnotation(UserNameBinding.class);

        if(ubc != null || ubm != null){
            context.register(UserFilter.class);
        }
    }

}