package com.vehicles;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.exceptions.CustomExceptions;
import com.exceptions.ErrorNumbers;

@Path("vehicle/location")
public class VehicleLookupService {

	private static final Logger LOGGER = Logger.getLogger(VehicleLookupService.class);
	Response resp=null;
	String x=null;
	
	@Path("/tracking")
	//@GET
	@POST
	//@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)	
public Response	trackerService(
		//@QueryParam("vehic") String vehicleID   //FOR GET
		
		@FormParam("vehic") String vehicleID          //For POST
		) {
	
		System.out.println("TEST 1"+vehicleID);
	VehicleLookupDB vehicleTrackerDB=new VehicleLookupDB();
	try {
		x=vehicleTrackerDB.getLocation(vehicleID);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		 CustomExceptions hasce =  new CustomExceptions().getBundledError(ErrorNumbers.SM_2002,e.getMessage().toString());
      		LOGGER.error("CLASS: UserLogin, METHOD: userService, ERROR :" + e.getMessage(),e);
      		return Response.ok(hasce.getErrorJsonObject(hasce.getErrorCode(),hasce.getErrorMessage())).build();	
		//e.printStackTrace();
	}
    resp=Response.ok(x).build();
	return resp;
		
}
}
