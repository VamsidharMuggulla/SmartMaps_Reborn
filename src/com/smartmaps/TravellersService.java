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

@Path("update/travellers/")
public class TravellersService {
	
	@Path("plus")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBusStops(
			@FormParam("busstopname") String busStopName) {
		String x="SERVICE NOT OK";
		TravellersDB tdb=new TravellersDB();
		try {
			x=tdb.plus(busStopName,"+");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(x).build();
	}

	@Path("minus")
	@GET
	public Response updateBusStops(		
			@QueryParam("busstopname") String busStopName) {
		String x="SERVICE NOT OK";
		TravellersDB tdb=new TravellersDB();
		try {
			x=tdb.plus(busStopName,"-");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(x).build();
	}

}
