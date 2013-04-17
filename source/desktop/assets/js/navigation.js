$(function() {
    $(window).bind('resize', function()
    {
        resizeMe();
        }).trigger('resize');
	$(window).trigger('hashchange');
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
	/* This is the site-switch, it takes care of switching the content from the menu.
	 * Beware of the syntax of links when you add them. Every new hashlink has to be of the form: "#link"/"extraInformation=1&extraInformation=2"
	 * We use the "/" to seperate the link from the information. This also means that the "/"-char can not be used in any other relation.
	 * 
	 * If you want to add sites to the switch. First add them to the /index.php file, then add their hashLink here in the switch case.
	 */

    var hashInfo = location.hash;    
	var hashArray = hashInfo.split("/"); // We use / instead of ? in our URL's (for $_GET), they do the exact same, but gives a different look
	var destination = hashArray[0];
	var info = hashArray[1];
	var destinationPath = "";
	
	switch(destination)
	{
		case "":
		case "#ownProfile":
			destinationPath = "sites/own_profile.php";
		break;
		
		case "#profiles":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#profilePicUpload":
			destinationPath = "script/profilePicUpload.php";
		break;
		
		case "#addRelation":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#picsManager":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#makePic":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#addPic":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#removePic":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#editPic":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#deletePic":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#depManager":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#depInfo":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#qrManager":
			destinationPath = "sites/qrmanager.php";
		break;
		
		case "#appManager":
			destinationPath = "sites/placeholder.html";
		break;
		
		case "#404":
		default:
			destinationPath = "404";
	}
	
	destinationPath += "?"+info;
	if(!postData){
		postData = "";
	}
	
	
	$.ajax({
		type: "POST",
		url: destinationPath,
		data: postData,
		success: function(result) { // result is the content that the php file 'ECHO's.
			//var obj = jQuery.parseJSON(result); // Parsing JSON for easy Data Acces
			$("#content").html(result);
		}
	});
}