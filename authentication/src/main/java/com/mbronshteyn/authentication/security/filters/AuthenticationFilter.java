package com.mbronshteyn.authentication.security.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Value("${auth.service.secret}")
	private String SECTER;
	
	@Value("${auth.service.issuer}")
	private String ISSUER;	
	
    @Context
    UriInfo uriInfo;
    
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authHeaderVal = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);	
		
		if(authHeaderVal == null || authHeaderVal.equals("")) {
			LOGGER.error("Authintication is Invalid");	
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());			
		}
		
		try {
			
			User user = validateToken(authHeaderVal);
			if (user != null) {
				requestContext.setSecurityContext(new SecurityContext() {
					@Override
					public Principal getUserPrincipal() {
						return new Principal() {
							@Override
							public String getName() {
								return user.getUserId();
							}
						};
					}

					@Override
					public boolean isUserInRole(String role) {
						String roles = user.getRole();
						return roles.contains(role);
					}

					@Override
					public boolean isSecure() {
						return uriInfo.getAbsolutePath().toString().startsWith("https");
					}

					@Override
					public String getAuthenticationScheme() {
						return "Token-Based-Auth-Scheme";
					}
				});
			}		
		} catch (JWTVerificationException e) {
			LOGGER.error("Authintication is Invalid");	
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}	
	}

	private User validateToken(String authHeaderVal) throws JWTVerificationException, UnsupportedEncodingException {

	    Algorithm algorithm = Algorithm.HMAC256(SECTER);
	    JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build(); //Reusable verifier instance
	    DecodedJWT jwt = verifier.verify(authHeaderVal);
		
	    User user = new User();
	    user.setUserId(jwt.getSubject());
	    user.setRole(jwt.getClaim("role").asString());
	    
		return user;
	}


	private class User {
		
		String userId;
		String role;
		
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		
		
	}
}
