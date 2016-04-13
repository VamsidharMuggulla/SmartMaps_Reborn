var latitude,longitude;
var tracking,dest;
function getLocation() 
{
    if (navigator.geolocation) 
	{
        navigator.geolocation.getCurrentPosition(showPosition);
    } 
	else 
	{ 
        document.writeln("Geolocation is not supported by this browser.");
    }
} 
function showPosition(position)
{
	latitude=position.coords.latitude;
	longitude=position.coords.longitude;
	//console.log(latitude);
	//console.log(longitude);
}
function initMap() {
	map = new google.maps.Map(document.getElementById('mapp'), {
	    zoom: 17,
	    center: {lat: 16.510252, lng: 80.6444612}
	  });
		map.setOptions({ minZoom: 17, maxZoom: 17});
		google.maps.visualRefresh = true;
	 
}
function trackVehicle()
{
	getLocation();
	  dest=document.getElementById("destination").value;
	  console.log(dest);
	  if(dest=="")
	  {
		  alert('Enter your destination');
	  }
	  else
	  {
		
		//console.log(dest);
		//console.log(lat);
		//console.log(lng);
		/*
  	(function($){
  		$.ajax({
  			
  		    url: 'http://localhost:8080/SmartMaps/track/nearest/vehicle',
  		    type: 'POST',
  		    data: 
  		    	{ 
  		    		latitude : lat, 
  		    		longitude: lng,
  		    		destination: dest
  		    	},
  		    dataType: 'text',
  		    contentType: 'application/x-www-form-urlencoded',
  		    success: function( data, textStatus, jQxhr ){
  		        resp = JSON.parse(data);
  		        console.log(data);   		        
  		    },
  		    error: function (jqXhr, textStatus, errorThrown) {
  		    	console.log(errorThrown);
  		    }
  		});
  		    })(jQuery); */
		tracking=setTimeout(trackVehicle, 5000);
	}
}
