<?php
require "db/new.db.php";

//Run function
$pictogram = makeJsonPictogram("testPic","3","","","Super Hest","hest fisk, ko,and.rasmus;læder:disko");
echo "Pictogram: ".$pictogram;
db_uploadePictogram($pictogram);

//Functions
function db_uploadePictogram($jsonPictogram){
	global $session,$username,$password;
	$data = '{
		"action": "create",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
	    "data": {
	    	"type":"pictogram",
	    	"values":['.$jsonPictogram.']
	    }
	}';
	$result = db_query($data);
	echo "<br><br>Result: ".$result['status'];
	/*if ($result['status'] == 'OK')
	{
		return $result['data'];
	}
	else
	{
		return false;
	}*/
}

function makeJsonPictogram($title,$privacy,$imageString,$soundString,$inlineText,$tagString){
	if($privacy == "0"){
		$privacyBool = "false";
	}else if($privacy == "1"){
		$privacyBool = "false";
	}
	else{//$privacy == "2"
		$privacyBool = "true";
	}
	
	$regex = "/[\t\s,.;:]+/";
	$tagArray = preg_split($regex,$tagString);
	if(count($tagArray)==0)
		$tagPrint = "null";
	else
		$tagPrint = '["'.implode('","',$tagArray).'"]';
	
	//If any of these are empty, make them contain the string NULL instead
	//Else add string start and end to the string
	if($imageString == "")
		$imageString = "null";
	else
		$imageString = '"'.$imageString.'"';
		
	if($soundString == "")
		$soundString = "null";
	else
		$soundString = '"'.$soundString.'"';
		
	if($inlineText == "")
		$inlineText = "null";
	else
		$inlineText = '"'.$inlineText.'"';
	
	return 
	'{ 
		"name": "'.$title.'", 
		"public": '.$privacyBool.', 
		"image": '.$imageString.', 
		"sound": '.$soundString.', 
		"text": '.$inlineText.', 
		"categories": null, 
		"tags": '.$tagPrint.' 
	}';
}
?>