(function($){
        function processForm( e ){
            $.ajax({
                url: 'http://smartmapsss.j.layershift.co.uk/SmartMaps/signup/user/register',
                dataType: 'text',
                type: 'post',
                contentType: 'application/x-www-form-urlencoded',
              data: $(this).serialize(),
                success: function( data, textStatus, jQxhr ){
                    console.log(data);
                    var resp = JSON.parse(data);
					registrationresp = resp[0]["registration"];  
					var bool=checkResponse();
					if(bool)
						{
						document.getElementById("errormsg1").style.color="green";
						document.getElementById("errormsg1").innerHTML="Successfully registered.";
						document.getElementById("user-name").value="";
						document.getElementById("user-email").value="";
						document.getElementById("user-pw").value="";
						document.getElementById("user-pw-repeat").value="";
						document.getElementById("user-mobile").value="";
						}
					else
						{
						document.getElementById("errormsg1").style.color="red";
						document.getElementById("errormsg1").innerHTML="Email already exists...";
						document.getElementById("user-name").value="";
						document.getElementById("user-email").value="";
						document.getElementById("user-pw").value="";
						document.getElementById("user-pw-repeat").value="";
						document.getElementById("user-mobile").value="";
						
				        document.getElementById("signup").disabled = false;
				        document.getElementById("signup").value = "Create Account";
						}
                },
                error: function( jqXhr, textStatus, errorThrown ){
                    console.log( errorThrown );
                }
            });

            e.preventDefault();
        }
        $('#signup').submit( processForm );
    })(jQuery);

function checkResponse()
{
	if(registrationresp=="already_registered")
		{
		return false;
		}
	else
		{
		return true;
		}
}

function checkpwd()
{
	//console.log("Hello buddy");
	var pwd=document.getElementById("user-pw").value;
	var cnfpwd=document.getElementById("user-pw-repeat").value;
	//console.log(pwd);
	if(pwd!=cnfpwd)
	{
		alert("Mismatch of passwords");
		document.getElementById("errormsg1").style.color="red";
		document.getElementById("errormsg1").innerHTML="Mismatch of passwords";
		//If u want u can remove above 2 lines
	}
	else
	{
		document.getElementById("errormsg1").innerHTML="";	
	}
}
