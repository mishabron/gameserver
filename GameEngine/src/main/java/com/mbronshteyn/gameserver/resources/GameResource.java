package com.mbronshteyn.gameserver.resources;

import com.mbronshteyn.data.vendor.Contact;
import com.mbronshteyn.gameserver.dto.game.*;
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
    @ApiOperation(value = "Authinticate Card", notes="authinticates Card before game")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Card Successfully Authinticated", response = CardDto.class)})
    public CardDto logingCard(@ApiParam(name = "authCard", value = "AuthinticateDto containing parameters to authinticate card", required = true) AuthinticateDto authDto){

        try {
            return gameService.logingCard(authDto);
        } catch (GameServerException e) {
            LOGGER.error("Card is not authinticated");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }

    @POST
    @Path("/hit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Hit Card", notes="Hit Card attempt")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Card Successfully hit", response = CardDto.class)})
    public CardDto hitCard(@ApiParam(name = "hitCard", value = "CardHitDto contaning card hit numbers", required = true) CardHitDto cardHitDto){

        try {
            return gameService.hitCard(cardHitDto);
        } catch (GameServerException e) {
            LOGGER.error("Card is not hit");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }

    @GET
    @Path("/winningPin/{game}/{cardNo}/{deviceId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Winning Pin", notes="Get Winning Pin")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Winning Pin is read", response = String.class)})
    public String getWinningPin(@ApiParam(name = "game", value = "Game Name", required = true) @PathParam("game") String game,
                                @ApiParam(name = "cardNo", value = "Card NUmber", required = true) @PathParam("cardNo") Long cardNo,
                                @ApiParam(name = "deviceId", value = "Device ID", required = true) @PathParam("deviceId") String deviceId){

        CardHitDto cardHitDto = new CardHitDto();
        cardHitDto.setGame(game);
        cardHitDto.setCardNumber(cardNo);
        cardHitDto.setDeviceId(deviceId);

        try {
            return gameService.getWinningPin(cardHitDto);
        } catch (GameServerException e) {
            LOGGER.error("Error getting winning pin");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }

    @POST
    @Path("/sendEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Save Email", notes="Sends Player Email")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Email is saved successfully")})
    public void sendEmail(@ApiParam(name = "winnerObect", value = "WinnerEmailDto containing card and email info", required = true) WinnerEmailDto winnerEmailDto){
        try {
            gameService.saveEmail(winnerEmailDto);
        } catch (GameServerException e) {
            LOGGER.error("Email is not saved");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }

    @POST
    @Path("/history")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Card play history", notes="teturns play history of a card")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Card Successfully Retrieved", response = HistoryDto.class)})
    public HistoryDto getHistory(@ApiParam(name = "authCard", value = "AuthinticateDto containing parameters to authenticate card", required = true) AuthinticateDto authDto){
        try {
            return gameService.getHistory(authDto);
        } catch (GameServerException e) {
            LOGGER.error("Card is not authinticated");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }

    @POST
    @Path("/freeAttempt")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Free Attempt\"", notes="Registers Free Attempt")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Free Attempt Successfully Updated", response = CardDto.class)})
    public CardDto saveFreeAttempt(@ApiParam(name = "authCard", value = "AuthinticateDto containing parameters for card", required = true) AuthinticateDto saveFreeAttempt){
        try {
            return gameService.saveFreeAttempt(saveFreeAttempt);
        } catch (GameServerException e) {
            LOGGER.error("Free Attempt is not saved");
            Response response = Response.status(e.getErrorStatus()).header("message",e.getMessage()).header("errorCode",e.getErrorCode().toString()).build();
            throw new WebApplicationException(response);
        }
    }
}
