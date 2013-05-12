<?php
require "db/new.db.php";

//Run function
$pictogram = makeJsonPictogram("testPic","3","","","Super Hest","Ko,spand . and;kat : land..spand");
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
	
	echo "<br><br>Data: ".$data;
	
	$result = db_query($data);
	echo "<br><br>Result: ".$result['status'];
	echo "<br><br>Error :".$result['errors'][0];
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
	if($tagArray[0]=="")
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
	
	
	$returnVar = '{ 
		"name": "'.$title.'", 
		"public": '.$privacyBool;
		
	if($imageString != "null")	
		$returnVar .= ',"image": '.$imageString;
	if($soundString != "null")
		$returnVar .= ',"sound": '.$soundString;
	if($inlineText != "null")
		$returnVar .= ',"text": '.$inlineText;
	if($tagPrint != "null")
		$returnVar .= ',"tags": '.$tagPrint;
	$returnVar .='}';
	
	return $returnVar;
}
?>