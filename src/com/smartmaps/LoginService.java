package com.smartmaps;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.exceptions.CustomExceptions;
import com.exceptions.ErrorNumbers;
//import javax.ws.rs.core.UriInfo;
@Path("user/loginservice")
public class LoginService {
	
	Response resp=null;
	String x=null;
	private static final Logger LOGGER = Logger.getLogger(LoginService.class);
	
	@Path("/log")
	@POST	
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userService(
			//@Context HttpHeaders hh,  //HttpHeader For Reading Cookies From Request
			//@Context UriInfo uriInfo,    
			@FormParam("username") String username,
			@FormParam("password") String password
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

		//String retString="";
		LoginDB loginValidate=new LoginDB();
		
		try {
			x = loginValidate.loginUser(username,password);
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