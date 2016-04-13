package com.smartmaps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpDB {
	
	Connection con;
	PreparedStatement ps;
	String statement;
	
	JSONArray jsona;
	JSONObject json;
	String user_name;
	
	public SignUpDB() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsona=new JSONArray();
		json = new JSONObject();
	
	}
	
	String sigupUser(String userName,String password,String email,String mobile,String cityTown) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");		
			System.out.println("Driver Loaded");		
			
			con=DriverManager.getConnection("jdbc:mysql://mysql122616-smartmapsss.j.layershift.co.uk/smart_maps","root","CVFrtf34966");
			//con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
			System.out.println("Connected To MySql");
			statement="INSERT INTO users("
					+ "username,password,city_town,email,mobile) "
					+ "values('"+userName+"','"+password+"','"+cityTown+"','"+email+"',"+mobile+");";
			
			ps=con.prepareStatement(statement);
			int x=ps.executeUpdate();	
			if(x==1) {
				System.out.println("INSERTED "+x);
				json.append("registration", "success");
				jsona.put(json);
				return jsona.toString();
			}
			else {
				System.out.println("No Not Registered : "+x);
				json.append("registration", "already_registered");
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


		try {
			json.append("registration", "already_registered");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsona.put(json);
		return jsona.toString();
	}
	
	
	
	
	String emailValidate(String email) throws SQLException {
		

		try {
			Class.forName("com.mysql.jdbc.Driver");		
			System.out.println("Driver Loaded");		
			con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
			System.out.println("Connected To MySql");
			statement="SELECT * FROM users WHERE email='"+email+"';";
			ps=con.prepareStatement(statement);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				
				System.out.println("Already Registered : "+rs.getString(1)+
						" : "+rs.getString(2)+
						" : "+rs.getString(3)+
						" : "+rs.getString(4)+
						" : "+rs.getString(5)
						);
				
				json.append("email", rs.getString(1));
				json.append("status", "	registered");
				jsona.put(json);
				
				return jsona.toString();
			}
			else
			{
				System.out.println("Not Registered : "+email);
				json.put("email", email);
				json.put("status","notregistered");
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
		return "SERVICE NOT OK";	}
}
