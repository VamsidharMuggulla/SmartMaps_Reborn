package com.smartmaps;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("locations/")
public class BusStopsService {

	@Path("list")
	//@GET
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response busService(
			//@QueryParam("latitude") String latitude, //for GET methods
			//@QueryParam("longitude") String longitude,
			//@QueryParam("cityTown") String cityTown,
			//@QueryParam("destination") String destination ) {
			@FormParam("latitude") String latitude,   //for POST methods
			@FormParam("longitude") String longitude,
			@FormParam("citytown") String cityTown,
			@FormParam("destination") String destination) {
		String x="SERVICE NOT OK";
		cityTown=cityTown.replace(" ", "");
		cityTown=cityTown.trim();
		System.out.println("TEST cityTown : "+cityTown);
		System.out.println("TEST Destination : "+destination);
		//Double lat=Double.parseDouble(latitude);
		//Double lon=Double.parseDouble(longitude);
		BusStopDB bDB=new BusStopDB();
		try {
			x=bDB.getStopsList(latitude, longitude, cityTown,destination);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(x).build();
	}
	
}

