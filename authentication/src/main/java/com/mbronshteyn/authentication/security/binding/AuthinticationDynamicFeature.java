package com.mbronshteyn.authentication.security.binding;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.model.AnnotatedMethod;

import com.mbronshteyn.authentication.security.filters.AuthenticationFilter;

@Provider
public class AuthinticationDynamicFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {

		final AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());
		
        RolesAllowed ra = am.getAnnotation(RolesAllowed.class);
        if (ra != null) {
        	context.register(AuthenticationFilter.class);
        }	
		
	}


}