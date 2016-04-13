var c=[];
var cookie=document.cookie;
//alert(cookie);
if(cookie)
{
	//alert("hi");
	var ck=document.cookie.split(";");
	for(var i=0; i<ck.length; i++) 
	{
		c[i] = ck[i];
	}
	var username=c[0].split("=");
	var token=c[1].split("=");
	var status=c[2].split("=");
	if(username=="null" && token=="null" && status=="not_registered")
	{
		window.location.replace("http://smartmapsss.j.layershift.co.uk/SmartMaps/loginsignup.html");
	}
	else
	{
		window.location.replace("http://smartmapsss.j.layershift.co.uk/SmartMaps/userhome.html");
	}
}
else
{
	window.location.replace("http://smartmapsss.j.layershift.co.uk/SmartMaps/loginsignup.html");
}	
