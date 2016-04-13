
package com.smartmaps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class BusStopDB {
	Connection con;
	PreparedStatement ps;
	String statement;
	JSONArray jsonMain;
	JSONArray jsonMain2;
	JSONArray jsonMain3;
	Double lat2[];
	Double lon2[];
	Double distance[]; 
	public BusStopDB() {
		// TODO Auto-generated constructor stub
		con=null;
		ps=null;
		statement="";
		jsonMain=new JSONArray();
		jsonMain2=new JSONArray();
		jsonMain3=new JSONArray();
	}
	String getStopsList(String la1,String lo1,String cityTown,String destination) throws SQLException {
		
		try {
			Double latitude=Double.parseDouble(la1);
			Double longitude=Double.parseDouble(lo1);
			Class.forName("com.mysql.jdbc.Driver");		
			System.out.println("Driver Loaded");			
			//con=DriverManager.getConnection("jdbc:mysql://vamsidhar.esy.es:3306/u958710114_smart?connectTimeout=3000","u958710114_smart","smartguy");
			 //https://mysql121977-smartmapsss.j.layershift.co.uk
			 con=DriverManager.getConnection("jdbc:mysql://mysql122616-smartmapsss.j.layershift.co.uk/smart_maps","root","CVFrtf34966");	 
			//con=DriverManager.getConnection("jdbc:mysql://localhost/smart_maps","smartguy","smartguy");		
			System.out.println("Connected To MySql");
			statement="SELECT distinct * FROM bus_stops b, vehicles v,vehicle_bustop_relation vb WHERE vb.vehicleid=v.vehicleid AND vb.busstopid=b.id AND v.destination='"+destination+"';";  
			//statement="SELECT * FROM bus_stops WHERE at_city_town='"+cityTown+"';";
			ps=con.prepareStatement(statement);
			ResultSet rs=ps.executeQuery();
			
			System.out.println(rs.wasNull());
			System.out.println("Coumn Count : "+rs.getMetaData().getColumnCount());
			
			int count=0;
			while(rs.next())
				count=count+1;
			
			System.out.println(count);
			if(count>=1) {
			lat2 = new Double[count];
			lon2 = new Double[count];
			distance=new Double[count];
			}
			rs.close();
			rs=ps.executeQuery();   //for extracting Actual Result
			DistanceCalculator dc=new DistanceCalculator();
			int i=0;
			while(rs.next()) {
				//System.out.println("hi :"+rs.getString("busstop_name"));
				JSONObject json=new JSONObject();
				lat2[i]=Double.parseDouble( rs.getString("latitude"));
				//System.out.println( rs.getString("latitude"));
				lon2[i]=Double.parseDouble( rs.getString("longitude"));
				//json.append(key, value)
				//System.out.println(" : 5");
				 distance[i]=dc.distance(latitude, longitude, lat2[i], lon2[i]);
				 json.append("distance",Double.parseDouble(new DecimalFormat("##.###").format(distance[i])));
				 json.append("busStop", rs.getString("busstop_name").toString());
				 json.append("latitude", rs.getString("latitude").toString());
				 json.append("longitude", rs.getString("longitude").toString());
				 json.append("travellers", rs.getString("travellers").toString());
				 jsonMain.put(json);
					i++;
			}
			int length=jsonMain.length();
			JSONArray newJSON=new JSONArray();
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			for (int m = 0; m < length; m++) {
			    jsonList.add(jsonMain.getJSONObject(m));
			}
			Collections.sort( jsonList, new Comparator<JSONObject>() {
			    public int compare(JSONObject a, JSONObject b) {
			        Double valA = null;// = new Double();
			        Double valB=null;// = new Double();
			      // System.out.println(" : 1");
			        try {
			            valA = Double.parseDouble(a.get("distance").toString().replace("[", "").replace("]", ""));
			            valB = Double.parseDouble(b.get("distance").toString().replace("[", "").replace("]", ""));
			      //      System.out.println("VAL A :"+valA+"VAL B :"+valB);
			        } 
			        catch (JSONException e) {
			            e.printStackTrace();
			        }
			        if(valA>valB)
			        return 1;
			        else 
			        	return -1;
			    }
			});	
			/*System.out.println("Actual List :"+jsonList.size());
			int listSize=jsonList.size();
			if(listSize>5)
			{
				for(int r=5;r<listSize;r++)
					jsonList.remove(5);
			}
			System.out.println("Short List :"+jsonList.size());*/
			//if(jsonList.size()>)
			for (int g = 0; g < jsonList.size(); g++) {
				JSONObject newjsonObject=new JSONObject();
				newjsonObject=jsonList.get(g);
			    newJSON.put(newjsonObject);
			}
			System.out.println(newJSON.toString());
		    return newJSON.toString();
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
		return "Database NOT OK";
}
	class DistanceCalculator
	{
		public Double distance(Double lat1, Double lon1, Double lat2, Double lon2) {
			
			//System.out.println(" : "+lon1+" : "+lon2);
			Double theta = lon1 - lon2;
			Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
			dist = Math.acos(dist);
			dist = rad2deg(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			return (dist);
		}

		
		/*::	This function converts decimal degrees to radians :*/
		
		private Double deg2rad(Double deg) {
			return (deg * Math.PI / 180.0);
		}

		
		/*::	This function converts radians to decimal degrees :*/
		
		private Double rad2deg(Double rad) {
			return (rad * 180 / Math.PI);
		}
	}
}

