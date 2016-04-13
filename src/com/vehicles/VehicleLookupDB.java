package com.vehicles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.exceptions.CustomExceptions;
import com.exceptions.ErrorNumbers;

public class VehicleLookupDB {


	
	Connection con;
	PreparedStatement ps;
	String statement;	
	JSONArray jsonArray;
	JSONObject jsonObject;
	String trackerID;
	String destination;
	private static final Logger LOGGER = Logger.getLogger(VehicleTrackerDB.class);
	public VehicleLookupDB() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsonArray=new JSONArray();
		jsonObject = new JSONObject();
	
	}
	String getLocation(String vehicleID) //throws SQLException
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");		
			System.out.println("Driver Loaded");
			con=DriverManager.getConnection("jdbc:mysql://mysql122616-smartmapsss.j.layershift.co.uk/smart_maps","root","CVFrtf34966");
			
			//con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
			System.out.println("Connected To MySql");	
		
			statement="SELECT clatitude,clongitude FROM vehicles WHERE vehicleid='"+vehicleID+"';";
			
			ps=con.prepareStatement(statement);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
			jsonObject.append("latitude",rs.getString("clatitude"));			
			jsonObject.append("longitude",rs.getString("clongitude"));			
			jsonArray.put(jsonObject);			 }
			return jsonArray.toString();
		}
		catch(Exception e) {
			//ps.close();
			//con.close();
			System.out.println("EX :"+e.getMessage());
			CustomExceptions hasce =  new CustomExceptions().getBundledError(ErrorNumbers.SM_2002,e.getMessage().toString());
	  		LOGGER.error("CLASS: VehicleLookupDB, METHOD: getStop, ERROR :" + e.getMessage(),e);
	  		return hasce.getErrorJsonObject(hasce.getErrorCode(),hasce.getErrorMessage());			
			//e.printStackTrace();
		}	
				
	}
	
	
}

