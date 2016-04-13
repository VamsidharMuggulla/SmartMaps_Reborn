package com.vehicles;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.ws.rs.Consumes;
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
import com.smartmaps.BusStopDB;
import com.smartmaps.LoginDB;
import com.smartmaps.LoginService;

@Path("/nearest")
public class TrackVehicleService {

	
	Response resp=null;
	String x=null;
	private static final Logger LOGGER = Logger.getLogger(TrackVehicleService.class);
	
	@Path("/vehicle")
	@POST
	//@GET
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userService(
			//@Context HttpHeaders hh,  //HttpHeader For Reading Cookies From Request
			//@Context UriInfo uriInfo,    
			//@QueryParam("latitude") String latitude,
			//@QueryParam("longitude") String longitude,
			//@QueryParam("destination") String destination
			@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude,
			@FormParam("destination") String destination
			) {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("/media/vamsi"
					+ "/OLD WINDOOWS1/SmartMaps/Elcipse_workspace/mars/"
					+ "RemoteSystemsTempFiles/SmartMaps/src/SM_ERROR.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("TEST 1 "+latitude+longitude+destination);
		
		//Double lat=Double.parseDouble(latitude);
		//Double lon=Double.parseDouble(longitude);
		TrackVehicleDB bDB=new TrackVehicleDB();
		try {
			x=bDB.getVehicle(latitude,longitude,destination);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(x).build();
	}
	
}
