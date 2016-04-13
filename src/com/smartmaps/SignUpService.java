package com.smartmaps;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/")
public class SignUpService {

	@Path("register")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userService(
			//@Context HttpHeaders hh,  //HttpHeader For Reading Cookies From Request
			//@Context UriInfo uriInfo,
			@FormParam("email") String email,
			@FormParam("username") String userName,
			@FormParam("password") String password,
			@FormParam("city_town") String cityTown,
			@FormParam("mobile") String mobile
			) throws SQLException {
		
		SignUpDB sn=new SignUpDB();
		String x=sn.sigupUser(userName, password, email, mobile, cityTown);
		
		//String x=email+userName+pasword+cityTown+mobile;
		
				return Response.ok(x).build();
		
	}

	@Path("email")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response emailService(
			//@Context HttpHeaders hh,  //HttpHeader For Reading Cookies From Request
			//@Context UriInfo uriInfo,
			@FormParam("email") String email			
			) throws SQLException {
		
		String x="NOT OK";
		SignUpDB sn=new SignUpDB();
		
			x=sn.emailValidate(email);
		
		
		
				return Response.ok(x).build();
		
	}
}
