package com.mbronshteyn.authentication.resources;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.mbronshteyn.data.authentication.User;
import com.mbronshteyn.data.authentication.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Component
@Path("/Authentication")
@Api(value = "/Authentication")
public class AuthenticationResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Value("${auth.service.secret}")
	private String SECRET;
	
	@Value("${auth.service.issuer}")
	private String ISSUER;	
	
	@Autowired
	UserRepository userRepository;

	@Value("${password}")
	private String masterPassword;
	
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Authenticate User", notes="Authenticate User and issue secutity token.")	
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfull Authentication", 
            responseHeaders = { @ResponseHeader(name = HttpHeaders.AUTHORIZATION, description = "Authenticated JWT", response = String.class)}),
            @ApiResponse(code = 401, message = "Unauthorized Access"),            
            @ApiResponse(code = 404, message = "Service not found"),            
            @ApiResponse(code = 500, message = "Internal server error")})    
    public Response authenticateUser(	@ApiParam(name = "login", value = "User Name or ID", required = true) @FormParam("login") String login,
    									@ApiParam(name = "password", value = "User Password", required = true) @FormParam("password") String password) {
        try {
 
            // Authenticate the user using the credentials provided
            User user = authenticate(login, password);
 
            // Issue a token for the user
            String token = issueToken(user);
 
            // Return the token on the response
            return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
 
        } catch (Exception e) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    
	private String issueToken(User user) {
		
		String token = null;
		
		try {
			
			LocalDateTime now = LocalDateTime.now().plusHours(1);
			Date expDate = Date.from(now.toInstant(OffsetDateTime.now().getOffset()));
			
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    token = JWT.create()
		        .withIssuer(ISSUER)
		        .withSubject(user.getUserId())
		        .withClaim("role", user.getRole().toString())
		        .withExpiresAt(expDate)
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
			LOGGER.error("UTF-8 encoding not supported",exception);			
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
			LOGGER.error("Invalid Signing configuration / Couldn't convert Claims",exception);				
		}
		
		return token;
	}

	private User authenticate(String login, String password) throws Exception {

		BasicTextEncryptor passwordEncryptor = new BasicTextEncryptor();
		passwordEncryptor.setPasswordCharArray(masterPassword.toCharArray());

		User user = userRepository.findByUserId(login);
		
		if(user == null) {
			LOGGER.error("Incorrect Login: "+ login);			
			throw new Exception("Invalid User Name Or Password");
		}

		String secretPassword = passwordEncryptor.decrypt(user.getPassword());

		if(!secretPassword.equals(password)){
			LOGGER.error("Incorrect Login: "+ login);
			throw new Exception("Invalid User Name Or Password");
		}

		return user;
	}
 

}
