package com.mbronshteyn.gameserver.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mbronshteyn.authentication.security.binding.UserNameBinding;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Component
@Provider
@Priority(Priorities.USER)
public class UserFilter implements ContainerRequestFilter {
    
    @Autowired
    SecurityUser securityUser;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String jwt = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        DecodedJWT token = JWT.decode(jwt);
        securityUser.setUser(token.getSubject());
    }
}
