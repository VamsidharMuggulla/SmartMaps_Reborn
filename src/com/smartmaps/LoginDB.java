package com.smartmaps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exceptions.CustomExceptions;
import com.exceptions.ErrorNumbers;


public class LoginDB {

	Connection con;
	PreparedStatement ps;
	String statement;
	
	JSONArray jsona;
	JSONObject json;
	String user_name;
	
	private static final Logger LOGGER = Logger.getLogger(LoginDB.class);
	public LoginDB() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsona=new JSONArray();
		json = new JSONObject();
	
	}
	
	
String loginUser(String email,String password) //throws SQLException
{
	

	try {
		Class.forName("com.mysql.jdbc.Driver");		
		System.out.println("Driver Loaded");		
		//con=DriverManager.getConnection("jdbc:mysql://vamsidhar.esy.es/u958710114_smart","u958710114_smart","smartguy");
		con=DriverManager.getConnection("jdbc:mysql://mysql122616-smartmapsss.j.layershift.co.uk/smart_maps","root","CVFrtf34966");
		
		//con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
		System.out.println("Connected To MySql");		
		statement="SELECT email,password,username FROM users "
				+ "WHERE email='"+email+"' AND password='"+password+"';";		
		ps=con.prepareStatement(statement);
		ResultSet rs=ps.executeQuery();
		
		int count=0;
		while(rs.next())
		{
			count=count+1;
		}
		rs.first();
		if(count==0)
		{			
			System.out.println("No User Found!! ");			
			json.append("status","not_registered");	
			
			// json.append("status","logged_in");
			 json.append("token","null");
			 json.append("user_name", "null");
			 json.append("email", "null");
			 
			ps.close();
			con.close();
			System.out.println(" Status  "+json.get("status"));
			System.out.println(json);
			jsona.put(json);
			String x=jsona.toString();
			return x;
		}
		else
		{	 
			 String uuid = UUID.randomUUID().toString().substring(20);
			 System.out.println("TOKEN :  uuid = " + uuid);
			 
			 user_name=rs.getString("username");
			 json.append("status","logged_in");
			 json.append("token", uuid);
			 json.append("user_name", rs.getString("username"));
			 json.append("email",rs.getString("email"));
			 System.out.println(" User Found!!");
			 ps.close();
			 con.close();
			System.out.println(json);
			System.out.println(" Status  "+json.get("status"));
			jsona.put(json);
			String x=jsona.toString();
			return x; 
		}
	}catch(Exception e) {
		//ps.close();
		//con.close();
		CustomExceptions hasce =  new CustomExceptions().getBundledError(ErrorNumbers.SM_2002,e.getMessage().toString());
  		LOGGER.error("CLASS: UserLogin, METHOD: userService, ERROR :" + e.getMessage(),e);
  		return hasce.getErrorJsonObject(hasce.getErrorCode(),hasce.getErrorMessage());	
	
		//e.printStackTrace();
	}
	
	//finally {
		
	//	if(con!=null)
		//	con.close();
//	}
	
	//ps.close();
	//con.close();
	
	//return "";
}

}