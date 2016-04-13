var citytown,dest,vehicleID,marker,lat,lng,address,map,mapp2,start,end,directionsService,directionsDisplay,panorama,startlat,startlng,entlat,endlng,resp;
var duratio;
var latitude22,longitude22;
var busstpresp=[];
var latresp=[];
var lngresp=[];
var distresp=[];
var travresp=[];
var geocoder,infowindow;
mapTwo=document.getElementById("tab2");
mapTwo.onclick=function(){
 //getLocation();
 var mapDivv = document.getElementById('mapp');
 mapp2 = new google.maps.Map(mapDivv, {
   center: {lat: 16.510252, lng: 80.6444612},
   zoom: 17
 });
 mapp2.setOptions({ minZoom: 17, maxZoom: 17});
	google.maps.visualRefresh = true;

 console.log("Secound Map Loaded");      
}

//LOGOUT

logout=document.getElementById("logoutt");
logout.onclick=function(){
    console.log("logged Out");
    
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
    document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC"; 
    document.cookie = "email=; expires=Thu, 01 Jan 1970 00:00:00 UTC"; 
    document.cookie = "status=; expires=Thu, 01 Jan 1970 00:00:00 UTC"; 
    window.location.replace("http://smartmapsss.j.layershift.co.uk/SmartMaps/loginsignup.html");
}
//logout ends



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
	lat=position.coords.latitude;
	lng=position.coords.longitude;
}

function initMap() 
{
	getLocation();
	directionsService = new google.maps.DirectionsService;
	directionsDisplay = new google.maps.DirectionsRenderer;
	map = new google.maps.Map(document.getElementById('map'), {
    zoom: 17,
    center: {lat: 16.510252, lng: 80.6444612}
  });
	map.setOptions({ minZoom: 17, maxZoom: 17});
	google.maps.visualRefresh = true;
 
 //var centerControlDiv = document.createElement('div');
 //var centerControl = new CenterControl(centerControlDiv, map);

 //centerControlDiv.index = 1;
 //map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(centerControlDiv);
 
 geocoder = new google.maps.Geocoder;
 infowindow = new google.maps.InfoWindow;
   if(window.addEventListener){
   window.addEventListener('load',function() {
   geocodeLatLng(geocoder, map, infowindow);
 });
   }
 directionsDisplay.setMap(map);
 input=(document.getElementById("address"));
 var autocomplete = new google.maps.places.Autocomplete(input);
 autocomplete.bindTo('bounds', map);
 infowindow = new google.maps.InfoWindow();
 marker = new google.maps.Marker({
   map: map,
   //label: 'S',
   animation: google.maps.Animation.DROP,
   anchorPoint: new google.maps.Point(0, -29)
 });
 marker.addListener('click', toggleBounce);

 autocomplete.addListener('place_changed', function() {
   infowindow.close();
   marker.setVisible(false);
   var place = autocomplete.getPlace();
   if (!place.geometry) {
     window.alert("Autocomplete's returned place contains no geometry");
     return;
   }
   // If the place has a geometry, then present it on a map.
   if (place.geometry.viewport) {
     map.fitBounds(place.geometry.viewport);
   } else {
     map.setCenter(place.geometry.location);
     map.setOptions({ minZoom: 17, maxZoom: 17});
   }

   marker.setPosition(place.geometry.location);
   marker.setVisible(true);
	lat=place.geometry.location.lat();
	lng=place.geometry.location.lng();
	//console.log(lat);
	//console.log(lng);
   address = '';
   if (place.address_components) {
	   citytown=place.address_components[0].long_name;
	   //citytown=trim(strText);
	   //console.log(" 1 : "+citytown);
	   //console.log(" 2 : "+place.address_components[0].long_name);
	   //console.log(" 3 : "+place.address_components[1].long_name);
	   
	   
     address = [
       (place.address_components[0] && place.address_components[0].short_name || ''),
       (place.address_components[1] && place.address_components[1].short_name || ''),
       (place.address_components[2] && place.address_components[2].short_name || '')
     ].join(' ');
   }
   infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
   infowindow.open(map, marker);
 }); 
}
	$(document).ready(function(){
		$("#button").click(function(){
	        $("#right-panel").toggle();
	    });
    $("#findme").click(function(){
		  
		  dest=document.getElementById("destination").value;
		  //console.log(citytown);
			//console.log(lat);
			//console.log(lng);
			//console.log(dest);
		(function($){
		$.ajax({
			
		    url: 'http://smartmapsss.j.layershift.co.uk/SmartMaps/bustops/locations/list',
		    type: 'POST',
		    data: 
		    	{
		    		citytown : citytown, 
		    		latitude : lat, 
		    		longitude: lng,
		    		destination: dest
		    	},
		    dataType: 'text',
		    contentType: 'application/x-www-form-urlencoded',
		    success: function( data, textStatus, jQxhr ){
		        resp = JSON.parse(data);
		        //console.log(data);
		        for(var i=0;i<resp.length;i++)
		     	   {
		     	   		busstpresp[i]= resp[i]["busStop"].toString();
		     	   		distresp[i]= resp[i]["distance"].toString(); 
		     	   		travresp[i]= resp[i]["travellers"].toString();
		     	   		latresp[i]= resp[i]["latitude"].toString();
		     	   		lngresp[i]= resp[i]["longitude"].toString();
		     	   }
		        responseHandler();
		        
		    },
		    error: function (jqXhr, textStatus, errorThrown) {
		    	console.log(errorThrown);
		    }
		});
		    })(jQuery);


	});
});

function geocodeLatLng(geocoder, map, infowindow) {
	  var latlng = {lat: parseFloat(lat), lng: parseFloat(lng)};
	  geocoder.geocode({'location': latlng}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      if (results[1]) {
	        map.setZoom(11);
	        var marker = new google.maps.Marker({
	          position: latlng,
	          map: map
	        });
	        marker.addListener('click', toggleBounce);
	        infowindow.setContent(results[1].formatted_address);
	        var city=results[1].formatted_address;
	        var ct=city.split(",");
	        citytown=ct[1];
	       // console.log(" 1 "+results[0].formatted_address);
	        //console.log(" 2 "+results[1].formatted_address);
	          //console.log(results[1].formatted_address);
	        infowindow.open(map, marker);
	          //document.getElementById("user-location").value=town;
	          } else {
	        window.alert('No results found');
	      }
	    } else {
	      window.alert('Geocoder failed due to: ' + status);
	    }
	  });
	}

function toggleBounce() 
{
	  if (marker.getAnimation() !== null) {
	    marker.setAnimation(null);
	  } else {
	    marker.setAnimation(google.maps.Animation.BOUNCE);
	  }
}

function responseHandler()
{
document.getElementById("b1").innerHTML=
	""+busstpresp[0]+"&nbsp;&nbsp;&nbsp;&nbsp;"+distresp[0]+"&nbsp;KM&nbsp;&nbsp;&nbsp;&nbsp;<br />Travellers:"+travresp[0]+"";

document.getElementById("b2").innerHTML=
	""+busstpresp[1]+"&nbsp;&nbsp;&nbsp;&nbsp;"+distresp[1]+"&nbsp;KM&nbsp;&nbsp;&nbsp;&nbsp;<br />Travellers:"+travresp[1]+"";

document.getElementById("b3").innerHTML=
	""+busstpresp[2]+"&nbsp;&nbsp;&nbsp;&nbsp;"+distresp[2]+"&nbsp;KM&nbsp;&nbsp;&nbsp;&nbsp;<br />Travellers:"+travresp[2]+"";

document.getElementById("b4").innerHTML=
	""+busstpresp[3]+"&nbsp;&nbsp;&nbsp;&nbsp;"+distresp[3]+"&nbsp;KM&nbsp;&nbsp;&nbsp;&nbsp;<br />Travellers:"+travresp[3]+"";

document.getElementById("b5").innerHTML=
	""+busstpresp[4]+"&nbsp;&nbsp;&nbsp;&nbsp;"+distresp[4]+"&nbsp;KM&nbsp;&nbsp;&nbsp;&nbsp;<br />Travellers:"+travresp[4]+"";
for (var i = 0; i < resp.length; i++) {
	marker = new google.maps.Marker({
	position: new google.maps.LatLng(latresp[i], lngresp[i]),
	map: map,
	icon: 'http://chart.apis.google.com/chart?chst=d_map_pin_icon&chld=bus|FFFF00',
	title: busstpresp[i]
	});
}
}
var lat1,lng1,busstopname;
function bstp1()
{
	lat1=parseFloat(latresp[0]);
	lng1=parseFloat(lngresp[0]);
	busstopname=busstpresp[0];
	calcRoute();
}
function bstp2()
{
	lat1=parseFloat(latresp[1]);
	lng1=parseFloat(lngresp[1]);
	busstopname=busstpresp[1];
	calcRoute();
}
function bstp3()
{
	lat1=parseFloat(latresp[2]);
	lng1=parseFloat(lngresp[2]);
	busstopname=busstpresp[2];
	calcRoute();
}
function bstp4()
{
	lat1=parseFloat(latresp[3]);
	lng1=parseFloat(lngresp[3]);
	busstopname=busstpresp[3];
	calcRoute();
}
function bstp5()
{
	lat1=parseFloat(latresp[4]);
	lng1=parseFloat(lngresp[4]);
	busstopname=busstpresp[4];
	calcRoute();
}


function calcRoute() {
	directionsDisplay.setPanel(document.getElementById('right-panel'));
	startlat=parseFloat(lat);
	startlng=parseFloat(lng);
	start={lat: startlat, lng: startlng};
	end={ lat: lat1,lng:lng1};
	  var request = {
	      origin: start,
	      destination: end,
	      travelMode: google.maps.TravelMode.WALKING
	  };
	  directionsService.route(request, function(response, status) {
	    if (status == google.maps.DirectionsStatus.OK) {
	       directionsDisplay.setDirections(response);
	      var route = response.routes[0];
	      console.log(route);
	      var Start=route.legs[0].start_address;
	      var array = Start.split(',');
	      //console.log(array);
	      var End=route.legs[0].end_address;
	      var array1 = End.split(',');
	      var Distance=route.legs[0].distance.text;
	      var Duration=route.legs[0].duration.text;
	      var step=[];
	      for(var i=0;i<route.legs[0].steps.length;i++)
	      {
	    	  step[i]=route.legs[0].steps[i].instructions;
	    	  //console.log("Step 1",route.legs[0].steps[1].instructions);
	    	  //console.log("Step 1",route.legs[0].steps[2].instructions);
	      }
	      /* var summaryPanel = document.getElementById('right-panel');
	      summaryPanel.innerHTML = '';
	      //console.log(route.legs.length);
	      for (var i = 0; i < route.legs.length; i++) {
	        var routeSegment = i + 1;
	        summaryPanel.innerHTML += 'From&nbsp;:&nbsp;';
	        summaryPanel.innerHTML += array[0];
	        summaryPanel.innerHTML += array[1];
	        summaryPanel.innerHTML += array[2] + '<br> To&nbsp;:&nbsp;';
	        summaryPanel.innerHTML += array1[0];
	        summaryPanel.innerHTML += array1[1];
	        summaryPanel.innerHTML += array1[2] + '<br> Distance&nbsp;:&nbsp;';
	        summaryPanel.innerHTML += Distance + '<br>';
	        summaryPanel.innerHTML +=step;
	      } */
	    }
	  });
	  (function($){
		  $.ajax({
			  url: 'http://smartmapsss.j.layershift.co.uk/SmartMaps/locdetails/update/travellers/plus',
		      type: 'POST',
		      data: 
		      	{
		    	  	busstopname : busstopname
		      	},
		      dataType: 'text',
		      contentType: 'application/x-www-form-urlencoded',
		      success: function( data, textStatus, jQxhr ){
		    	  //console.log(data);
		    	  responseHandler();
		          //var resp = JSON.parse(data);
		      },
		      error: function (jqXhr, textStatus, errorThrown) {
		      	console.log(errorThrown);
		      }
		  });
		      })(jQuery);
}

function trackVehicle()
{
	getLocation();
	  dest1=document.getElementById("destination").value;
	  if(dest1=='')
	  {
		  alert('Enter your destination');
	  }
	  else
	  {
		
		console.log("Track Nearest Vehicle Current : "+dest1);
		console.log("TNV la:"+lat);
		console.log("TNV lo"+lng);
    	(function($){
    		$.ajax({
    			
    		    url: 'http://smartmapsss.j.layershift.co.uk/SmartMaps/track/nearest/vehicle',
    		    type: 'POST',
    		    data: 
    		    	{ 
    		    		latitude : lat, 
    		    		longitude: lng,
    		    		destination: dest1
    		    	},
    		    dataType: 'text',
    		    contentType: 'application/x-www-form-urlencoded',
    		    success: function( data, textStatus, jQxhr ){
    		        resp = JSON.parse(data);
    		        console.log(data);
    		        vehicleID=resp[0]["vehicleid"];    		      
    		        distance=resp[0]["distance"];
    		        longitude=resp[0]["longitude"];
    		        latitude=resp[0]["latitude"];
    		        var displayDist = document.getElementById('dist');
    		        displayDist.innerHTML = 'The Vehicle is ';
    		        displayDist.innerHTML +=distance;
    		        displayDist.innerHTML +='KM Far From You!!! ';
    		        //console.log(vehicleid);
    		        //console.log(distance);
    		        //console.log(longitude);
    		        //console.log(latitude);
    		        iWillTrackU();
    		    },
    		    error: function (jqXhr, textStatus, errorThrown) {
    		    	console.log(errorThrown);
    		    }
    		});
    		    })(jQuery);
	}
}
function iWillTrackU() 
{
	(function($){
		console.log("LookUp:"+vehicleID);	
		$.ajax({
	
		    url: 'http://smartmapsss.j.layershift.co.uk/SmartMaps/display/vehicle/location/tracking',
		    type: 'POST',
		    data: 
		    	{
		    	
		    	vehic: vehicleID[0]
		    	
		    	 	},
		    dataType: 'text',
		    contentType: 'application/x-www-form-urlencoded',
		    success: function( data, textStatus, jQxhr ){
		    	//console.log("Hi");
		        resp = JSON.parse(data);
		        console.log(data); 
		        longitude22=resp[0]["longitude"];
		        latitude22=resp[0]["latitude"];
		        console.log("22 : "+lonsgitude22);
		        console.log("22 : "+latitude22);
		    },
		    error: function (jqXhr, textStatus, errorThrown) {
		    	console.log(errorThrown);
		    }
		});
		    })(jQuery);
	tracking=setTimeout(trackVehicle, 10000);
	findTime();
	geocodeLatLng2(geocoder,mapp2,infowindow);
}

function geocodeLatLng2(geocoder, map, infowindow) {
	  var latlng = {lat: parseFloat(latitude22), lng: parseFloat(longitude22)};
	  geocoder.geocode({'location': latlng}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      if (results[1]) {
	        map.setZoom(17);
	        var marker = new google.maps.Marker({
	          position: latlng,
	          map: map
	        });
	      
	        infowindow.setContent(results[1].formatted_address);
	        infowindow.open(map, marker);
	        
	          } else {
	        window.alert('No results found');
	      }
	    } else {
	      //window.alert('Geocoder failed due to: ' + status);
	    }
	  });
	}





//@Auther Vam


function findTime() {
	directionsDisplay.setPanel(document.getElementById('time'));
	startlat22=parseFloat(lat);
	startlng22=parseFloat(lng);
	start={lat: startlat, lng: startlng};
	end={ lat: parseFloat(latitude22),lng:parseFloat(longitude22)};
	  var request = {
	      origin: start,
	      destination: end,
	      travelMode: google.maps.TravelMode.DRIVING
	  };
	  directionsService.route(request, function(response, status) {
	    if (status == google.maps.DirectionsStatus.OK) {
	    	
	       
	      
	      //duratio=response.routes[0].legs[0].duration.value;
	      document.getElementById('timee').innerHTML="The Vehicle reaches the stop in "+response.routes[0].legs[0].duration.text;
	      //console.log("OMG"+duratio);
	      
	      
	      //var tion=response.routes[0].legs[0].duration.text;
	      //console.log("Vam : Duration :"+tion);
	      //alert("Vam : Duration :"+tion);      
	      
	      
	     }
	  });	
	  
}
