package com.mbronshteyn.gameserver.resources;

import com.mbronshteyn.data.vendor.Contact;
import com.mbronshteyn.gameserver.dto.game.AuthinticateDto;
import com.mbronshteyn.gameserver.dto.game.CardDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.GameService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;

@Component
@Path("/Game")
@Api(value = "/game")
@ApiResponses(value = {
        @ApiResponse(code = 500, message = "Internal server error")})
public class GameResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    GameService gameService;


    @POST
    @Path("/authinticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Authinticate Card", notes="uthinticates Card before game")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Card Successfully Authinticated", response = CardDto.class)})
    public CardDto logingCard(@ApiParam(name = "authCard", value = "AuthinticateDto contaning parameters to authinticate card", required = true) AuthinticateDto authDto){

        try {
            return gameService.logingCard(authDto);
        } catch (GameServerException e) {
            LOGGER.error("Card is not authinticated");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }
}