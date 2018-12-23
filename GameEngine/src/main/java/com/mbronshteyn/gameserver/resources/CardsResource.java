package com.mbronshteyn.gameserver.resources;

import com.mbronshteyn.gameserver.dto.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.CardService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;

@Component
@Path("/Cards")
@RolesAllowed({"LOTOROLA_MANAGER"})
@Api(value = "/cards")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "User not Unauthorized"),
        @ApiResponse(code = 403, message = "Access Forbidden for this user"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")})
public class CardsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @HeaderParam(HttpHeaders.AUTHORIZATION)
    String jwt;

    @Autowired
    CardService distributorService;


    @POST
    @Path("/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Genarate Cards", notes="Generate Cards for the Batch.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful Card generation", response = String.class)})
    public Response generateCards(@ApiParam(name = "batch", value = "new batch", required = true) BatchDto batch) {

        try {
            distributorService.generateCardsForBatch(batch);
            return Response.ok().build();

        } catch (GameServerException e) {
            LOGGER.error("Error creating Cards");
            Response response = Response.status(e.getErrorStatus()).build();
            throw new WebApplicationException(response);
        }

    }
}
