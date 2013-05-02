$(document).ready(function() {
	  // Handler for .ready() called.
		document.getElementById("profile").change( function(){
		  // check input ($(this).val()) for validity here
		  if($(this).val() == "guardien"){} 
		});
});

function zipCode(str)
{
if (str.length==0)
  { 
  document.getElementById("profile").change( function() {
  // check input ($(this).val()) for validity here
  if($(this).val() == "guardien"){}
});
  return ;
  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","creat_profile.php?q="+str,true);
xmlhttp.send();
}
