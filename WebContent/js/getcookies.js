var c=[];
var cookie=document.cookie;
//alert(cookie);
if(cookie)
{
	/*alert("hi");
	var ck=document.cookie.split(";");
	for(var i=0; i<ck.length; i++) 
	{
		c[i] = ck[i];
	}
	var username=c[0].split("=");
	var token=c[1].split("=");
	//var status=c[2].split("="); */
	if(getCookie("username")=="" && getCookie("token")=="" ) //&& status=="not_registered")
	{

	      //createCookie(username,"",-1);
	      //createCookie(token,"",-1);
	      //createCookie(email,"",-1);
	      //createCookie(status,"",-1);
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

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}