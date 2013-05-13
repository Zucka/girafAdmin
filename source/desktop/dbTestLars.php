<?php
require "db/new.db.php";

//Run function
$pictogram = makeJsonPictogram("testPic","3","","","Super Hest","Ko,spand . and;kat : land..spand");
echo "Pictogram: ".$pictogram;
db_uploadePictogram($pictogram);

//Functions
function db_uploadeProfilePic($profileImage){
	global $session,$username,$password;
	$data = '{
		"action": "update",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
	    "data": {
	    	"type":"profile",
	    	"values":['.$jsonPictogram.']
	    }
	}';
	
	$result = db_query($data);

	if ($result['status'] == 'OK')
	{
		return $result['data'];
	}
	else
	{
		return false;
	}
}

function makeJsonProfilePic($id,$profileImage){
	'[{ 
		"id": '.$id.', 
		"value": value_object 
	}]';
}

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

	if ($result['status'] == 'OK')
	{
		return $result['data'];
	}
	else
	{
		return false;
	}
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
	
	//Handle Tag Array with various splits
	$regex = "/[\t\s,.;:]+/";
	$tagArray = preg_split($regex,$tagString);
	if($tagArray[0]=="")
		$tagPrint = "";
	else
		$tagPrint = '["'.implode('","',$tagArray).'"]';

	$returnVar = '{ 
		"name": "'.$title.'", 
		"public": '.$privacyBool;
		
	//If any of these are empty, don't include them in the JSON
	if($imageString != "")	
		$returnVar .= ',"image": '.$imageString;
	if($soundString != "")
		$returnVar .= ',"sound": '.$soundString;
	if($inlineText != "")
		$returnVar .= ',"text": '.$inlineText;
	if($tagPrint != "")
		$returnVar .= ',"tags": '.$tagPrint;
	$returnVar .='}';
	
	return $returnVar;
}

	echo "<br><br>Data: ".$data;
	echo "<br><br>Result: ".$result['status'];
	echo "<br><br>Error :".$result['errors'][0];
?>