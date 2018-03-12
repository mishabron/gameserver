package com.mbronshteyn.gameserver.resources;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbronshteyn.gameserver.data.Contact;
import com.mbronshteyn.gameserver.data.Distributor;
import com.mbronshteyn.gameserver.data.Vendor;
import com.mbronshteyn.gameserver.dto.ContactDto;
import com.mbronshteyn.gameserver.dto.DistributorDto;
import com.mbronshteyn.gameserver.dto.VendorDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.DistributorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Path("/Distributors")
@RolesAllowed({"LOTOROLA_MANAGER"})	
@Api(value = "/distributors")
@ApiResponses(value = {           
        @ApiResponse(code = 401, message = "User not Unauthorized"),
        @ApiResponse(code = 403, message = "Access Forbidden for this user"),                        
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")})
public class DistributorResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Context  //injected response proxy supporting multiple threads
	private HttpServletResponse response;

	@HeaderParam(HttpHeaders.AUTHORIZATION) 
	String jwt;
	
	@Autowired
	DistributorService distributorService;
				
	@GET
    @Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Distributor", notes="Retrieve Distributor record.")	
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful retrieval of Distributor ", response = Distributor.class)})	
    public Distributor getDistributor(@ApiParam(name = "id", value = "ID for given distributor", required = true)  
    		@QueryParam("id")  Long distributorId) {
        return distributorService.getDistributor(distributorId);
    }
	
	@GET
    @Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve All Distributors", notes="Retrieve All Distributor records.")	
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful retrieval of Distributors ", response = Distributor.class,responseContainer = "List")})	
    public List<Distributor> getAllDistributors() {
        return distributorService.getDistributors();
    }	
	
	
	@PUT
    @Path("/distributor")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add new Distributor", notes="Creates new Distributor")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful Contact update", response = Distributor.class)})		
	public Distributor addDistributor(@ApiParam(name = "distributor", value = "new distributor record", required = true)  DistributorDto distributor) {
		
		Distributor newDist = null;
		try {
			newDist = distributorService.addDistributor(distributor,jwt);
		} catch (GameServerException e) {
			LOGGER.error("Error creating Distributor");			
		    Response response = Response.status(e.getErrorStatus()).build();
		    throw new WebApplicationException(response);			
		}
		
		return newDist;		
	}
	
	@GET
    @Path("/Contacts/get")
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Contacts for Distributor", notes="Retrieve Contacts for Distributor.")	
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful retrieval of Contacts ", response = Contact.class,responseContainer = "List")})	
    public List<Contact> getContacts(@ApiParam(name = "id", value = "ID for given distributor", required = true)  
    		@QueryParam("id")  Long distributorId) {
        return distributorService.getContacts(distributorId);
    }
	
	@PUT
    @Path("/Contacts/contact")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add new Contact to Distributor", notes="Adds new Contact to Distributor.")	
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful creation of Contact ", response = Contact.class)})	
    public Contact addContact(@ApiParam(name = "contactDto", value = "ContactDto contaning new Contact record", required = true) ContactDto contact) {
		
		Contact newContact = null;
		try {
			newContact = distributorService.addContact(contact,jwt);
		} catch (GameServerException e) {
			LOGGER.error("Error creating Contact");			
		    Response response = Response.status(e.getErrorStatus()).build();
		    throw new WebApplicationException(response);			
		}
        
        return newContact;
    }
	
	@GET
    @Path("/Vendors/get")
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Vandors for Distributor", notes="Retrieve Vendors for Distributor.")	
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful retrieval of Vendors ", response = Vendor.class,responseContainer = "List")})	
    public List<Vendor> getVendors(@ApiParam(name = "id", value = "ID for given distributor", required = true)  
    		@QueryParam("id")  Long distributorId) {
        return distributorService.getVendors(distributorId);
    }
	
	@PUT
    @Path("/Vendors/vendor")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add new Vendor to Distributor", notes="Adds new Vendor to Distributor.")	
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful creation of Vendor ", response = Contact.class)})	
    public Vendor addVendor(@ApiParam(name = "vendorDto", value = "VendorDto contaning new Vendor record", required = true) VendorDto vendor) {
		
		Vendor newVendor = null;
		try {
			newVendor = distributorService.addVendor(vendor,jwt);
		} catch (GameServerException e) {
			LOGGER.error("Error creating Vendor");			
		    Response response = Response.status(e.getErrorStatus()).build();
		    throw new WebApplicationException(response);			
		}
        
        return newVendor;
    }	
}

