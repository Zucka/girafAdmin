$(function() {
    $(window).bind('resize', function()
    {
        resizeMe();
        }).trigger('resize');
    });
	
function resizeMe(){
	//Standard height, for which the body font size is correct
	var preferredHeight = 768;  

	var displayHeight = $(window).width();
	var percentage = displayHeight / preferredHeight;
	var newFontSize = Math.floor(9 * percentage) - 1;
	if(newFontSize < 10){
		newFontSize = 10;
	}
	$("body").css("font-size", newFontSize);
}

window.onhashchange = function(){
    var hashInfo = location.hash;    
	var hashArray = hashInfo.split("/");
	var destination = hashArray[0];
	var info = hashArray[1];
	var destinationPath = "";
	
	switch(destination)
	{
		case "":
		case "#ownProfile":
			destinationPath = "sites/own_profile.html";
		break;
		
		case "#profiles":
			destinationPath = "";
		break;
		
		case "#addRelation":
			destinationPath = "";
		break;
		
		case "#picsManager":
			destinationPath = "";
		break;
		
		case "#makePic":
			destinationPath = "";
		break;
		
		case "#addPic":
			destinationPath = "";
		break;
		
		case "#removePic":
			destinationPath = "";
		break;
		
		case "#editPic":
			destinationPath = "";
		break;
		
		case "#deletePic":
			destinationPath = "";
		break;
		
		case "#depManager":
			destinationPath = "";
		break;
		
		case "#depInfo":
			destinationPath = "";
		break;
		
		case "#qrManager":
			destinationPath = "";
		break;
		
		case "#appManager":
			destinationPath = "";
		break;
		
		default:
			destinationPath = "404";
	}
	
	$.ajax({
		type: "POST",
		url: destinationPath,
		data: "",
		success: function(result) { // result is the content that the php file 'ECHO's.
			//var obj = jQuery.parseJSON(result); // Parsing JSON for easy Data Acces
			$("#content").html(result);
		}
	});
}