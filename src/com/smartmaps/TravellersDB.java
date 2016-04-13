package com.smartmaps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TravellersDB {
	
	Connection con;
	PreparedStatement ps;
	String statement;
	
	JSONArray jsona;
	JSONObject json;
	String user_name;
	
	public TravellersDB() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsona=new JSONArray();
		json = new JSONObject();
	
	}
	
	String plus(String busStopName,String op) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");		
				System.out.println("Driver Loaded");		
			
				con=DriverManager.getConnection("jdbc:mysql://mysql122616-smartmapsss.j.layershift.co.uk/smart_maps","root","CVFrtf34966");		
				//con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
			System.out.println("Connected To MySql");
			statement="UPDATE bus_stops SET travellers=travellers"+op+"1 WHERE busstop_name='"+busStopName+"';";
			
			ps=con.prepareStatement(statement);
			int x=ps.executeUpdate();	
			if(x==1) {
				System.out.println("UPDATED DB : "+x);
				json.append("update", "success");
				jsona.put(json);
				return jsona.toString();
			}
			else {
				System.out.println("No Not UPDATED : "+x);
				json.append("update", "failure");
				jsona.put(json);
				return jsona.toString();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {	
			if(con!=null)
				con.close();
		}
		ps.close();
		con.close();
		return "";
}
}