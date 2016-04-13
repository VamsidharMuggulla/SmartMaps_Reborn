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

public class VehicleTrackerDB {

	
	Connection con;
	PreparedStatement ps;
	String statement;	
	JSONArray jsonArray;
	JSONObject jsonObject;
	String trackerID;
	String destination;
	private static final Logger LOGGER = Logger.getLogger(VehicleTrackerDB.class);
	public VehicleTrackerDB() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsonArray=new JSONArray();
		jsonObject = new JSONObject();
	
	}
	String getStop(String vehicleID,
			       String clatitude,String clongitude,
			       String status) //throws SQLException
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");		
			System.out.println("Driver Loaded");
			con=DriverManager.getConnection("jdbc:mysql://mysql122616-smartmapsss.j.layershift.co.uk/smart_maps","root","CVFrtf34966");
			
			//con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
			System.out.println("Connected To MySql");	
		
			statement="UPDATE vehicles SET clatitude='"+clatitude+"',clongitude='"+clongitude+"',status='"+status+"' WHERE vehicleid='"+vehicleID+"';";
			
			
			ps=con.prepareStatement(statement);
			int x=ps.executeUpdate();	
			if(x==1) {
				System.out.println("INSERTED "+x);
				jsonObject.append("update", "success");
				jsonArray.put(jsonObject);
				return jsonArray.toString();
			}
			else {
				System.out.println("No Not Registered : "+x);
				jsonObject.append("update", "failed");
				jsonArray.put(jsonObject);
				return jsonArray.toString();
			}
			
		}
		catch(Exception e) {
			//ps.close();
			//con.close();
			CustomExceptions hasce =  new CustomExceptions().getBundledError(ErrorNumbers.SM_2002,e.getMessage().toString());
	  		LOGGER.error("CLASS: VehicleTrackerDB, METHOD: getStop, ERROR :" + e.getMessage(),e);
	  		return hasce.getErrorJsonObject(hasce.getErrorCode(),hasce.getErrorMessage());			
			//e.printStackTrace();
		}	
				
	}
	
	
}
