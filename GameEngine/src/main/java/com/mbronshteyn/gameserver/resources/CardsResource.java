package com.mbronshteyn.gameserver.resources;

import com.mbronshteyn.authentication.security.binding.UserNameBinding;
import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.gameserver.dto.card.BatchDto;
import com.mbronshteyn.gameserver.dto.card.BonusGenDto;
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
@RolesAllowed({"LOTOROLA_MANAGER","VENDOR"})
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
    CardService cardService;

    @POST
    @Path("/generate")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Genarate Cards", notes="Generate Cards for the CardBatch.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful Card generation", response = String.class)})
    @UserNameBinding
    public Response generateBatch(@ApiParam(name = "batch", value = "new batch", required = true) BatchDto batch) {

        try {

            CardBatch newBatch = cardService.generateCardsForBatch(batch);
            return Response.ok().build();

        } catch (GameServerException e) {
            LOGGER.error("Error creating Cards");
            Response response = Response.status(e.getErrorStatus()).build();
            throw new WebApplicationException(e.getMessage(),response);
        }

    }

    @POST
    @Path("/activate/card")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Activate Card", notes="Activate single card.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful Card Activation", response = String.class)})
    @UserNameBinding
    public Response activateCard(@ApiParam(name = "barCode", value = "barcode", required = true) String barcode) {

        try {

            Card card = cardService.activateCard(barcode);
            return Response.ok().build();

        } catch (GameServerException e) {
            LOGGER.error("Error activating Card. Barcode: "+barcode);
            Response response = Response.status(e.getErrorStatus()).build();
            throw new WebApplicationException(e.getMessage(),response);
        }
    }

    @POST
    @Path("/activate/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Activate Batch", notes="Activate all cards in the batch.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful Card Activation", response = String.class)})
    @UserNameBinding
    public Response activateBatch(@ApiParam(name = "barCode", value = "barcode", required = true) String barcode) {

        try {

            CardBatch batch = cardService.activateBatch(barcode);
            return Response.ok().build();

        } catch (GameServerException e) {
            LOGGER.error("Error activating Card. Barcode: "+barcode);
            Response response = Response.status(e.getErrorStatus()).build();
            throw new WebApplicationException(e.getMessage(),response);
        }
    }

    @POST
    @Path("/generate/bonuses")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Genarate Bonuses", notes="Generate Bonuses for the CardBatch.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful Card generation", response = String.class)})
    @UserNameBinding
    public Response generateBonuses(@ApiParam(name = "generateBonuses", value = "BonusGenDto contaning bonuses data", required = true) BonusGenDto bonusGenDto) {

        try {
            cardService.generateBonuses(bonusGenDto);
            return Response.ok().build();
        } catch (GameServerException e) {
            LOGGER.error("Error creating Cards");
            Response response = Response.status(e.getErrorStatus()).build();
            throw new WebApplicationException(e.getMessage(),response);
        }

    }
}
