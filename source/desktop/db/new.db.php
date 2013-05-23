<?php
if( ! $_SESSION)
{
	session_start();
}
if (isset($_SESSION['dbsess'])) {$session = $_SESSION['dbsess'];} else {$session = '';}
if (isset($_SESSION['username'])) {$username = $_SESSION['username'];} else {$username = '';}
if (isset($_SESSION['dbsess'])) {$password = $_SESSION['password'];} else {$password = '';}
if (isset($_SESSION['userId'])) {$userId = $_SESSION['userId'];} else {$userId = '';}


function db_query($json)
{
	$address = '130.225.196.27';
	$port = 2468;
	$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
	socket_connect($socket, '130.225.196.27',2468);
	socket_write($socket, $json, strlen($json));
	//sleep(0.1);
	$buf = '';
	$ret = '';
	if (false !== ($bytes = socket_recv($socket, $buf, 2048000, MSG_WAITALL))) {
		$ret .= utf8_encode($buf);
	}
	// echo $ret;
	// echo "</br>";

	return json_decode($ret,true);
}
/* Returns an array with session and user(userid) if authentication was succesful, FALSE otherwise */
function db_getSession($username,$password)
{
	$data = '{
		"action": null,
	    "auth": {
	        "username": "'.$username.'",
	        "password": "'.$password.'"
	    },
	    "data": null
	}';

	$result = db_query($data);
	if ($result['status'] != 'OK')
	{
		return FALSE;
	}
	else
	{
		return array('session' => 'asdf','user' => $result['session']['user'], 'profile' => $result['session']['profile']); //temporary session
		//return $result['session']['session']; //Uncomment when session is implemented
	}

}

/* 
	gets an array of names of users and their certificates from a list of users using ID
	--inputs--
	session should be a valid DB session
	ids should be an array of ids as ints

	--returns--
	if succesful, returns an associative array of names and their certificate
	if failure, returns FALSE
 */
function db_getCertificatesFromIds($session,$ids)
{
	global $session;
	$dataUser = array(
		'auth' => array(
			'session' => $session
		),
		'action' => 'read',
		'data' => array(
			'type' => 'user',
			'view' => 'list',
			'ids' => $ids
		)
	);
	$resultUser = db_query($dataUser);
	if ($resultUser['status'] != 'OK')
	{
		return FALSE;
	}

	$dataProfile = array(
		'auth' => array(
			'session' => $session
		),
		'action' => 'read',
		'data' => array(
			'type' => 'profile',
			'view' => 'list',
			'ids' => $ids
		)
	);

	$resultProfile = db_query($dataProfile);
	if ($resultProfile['status'] != 'OK')
	{
		return FALSE;
	}
	$result = array();
	$count = count($resultUser['data']);
	for ($i=0; $i < $count; $i++) { 
		$id = $resultUser['data'][i]['id'];
		$name = '';
		foreach ($resultProfile['data'] as $key => $value) {
			if ($value['id'] == $id)
			{
				$name = $value['name'];
				break;
			}
		}
		$result[] = array(
			'name' => $name,
			'certificate' => $resultUser['data'][i]['certificate']
		);
	}

	return $result;
}
/*
	Inserts a new qr code for a user

	--Inputs--
	$session should be a valid DB session
	$userId should be the user to be updated
	$newQr should be a 512 character unique string 

*/
function db_insertNewQrCode($id,$newQr)
{
	global $session,$username,$password;
	$data = '{
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
		"action": "update",
		"data": {
			"type": "user",
			"values": [
				{
					"id": '.$id.',
					"value": {
						"certificate": "'.$newQr.'",
						"id":'.$id.'
					}
				}
			]
		}
	}
	';
	$result = db_query($data);
	error_log($result['status']);
	if ($result['status'] == 'OK')
	{
		return TRUE;
	}
	else
	{
		return FALSE;
	}
}

/* 
	Creates a new user based on input
	--inputs--
	type should be type of user to create (0 = pedagouge; 1 = parrent; 2 = child)
	data should be array containing relevant information

	--returns--
	if succesful, returns an associative array of id, password and certificate
	if failure, returns FALSE
 */






function db_getProfiles(){
	global $session,$username,$password;
	$data = '{
		"action": "read",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
		"data": {
	    	"type":"profile",
	    	"view":"list",
	    	"ids":null
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

function db_getUsers(){
	global $session,$username,$password;
	$data = '{
		"action": "read",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
		"data": {
	    	"type":"user",
	    	"view":"list",
	    	"ids":null
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

function db_getProfileInfo($id){
	global $session,$username,$password;
	$data = '{
		"action": "read",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
		"data": {
	    	"type":"profile",
	    	"view":"details",
	    	"ids":['.$id.']
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

function db_getDepartmentInfo($departmentId){
	global $session,$username,$password;
	$data = '{
		"action": "read",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
	    "data": {
	    	"type":"department",
	    	"view":"details",
	    	"ids":['.$departmentId.']
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

function db_getGuardian_of($guardian_of){
	global $session,$username,$password;
	$data = '{
		"action": "read",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
	    "data": {
	    	"type":"profile",
	    	"view":"details",
	    	"ids":['.$guardian_of.']
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

function db_getRights($id)
{
	global $session,$username,$password;
	$data = '{
		"action": "read",
		"auth": {
			"username": "'.$username.'",
			"password": "'.$password.'"
		},
	    "data": {
	    	"type":"profile",
	    	"view":"list",
	    	"ids":null
	    }
	}';
	$result = db_query($data);
	if ($result['status'] != 'OK')
	{
		return false;
	}
	foreach ($result['data'] as $value) {
		if ($value['id'] == $id)
		{
			return array("update" => $value['update'],"delete" => $value['delete']);
		}
	}
}

/* picsManagerMake */

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
		$returnVar .= ',"image": "'.$imageString.'"';
	if($soundString != "")
		$returnVar .= ',"sound": "'.$soundString.'"';
	if($inlineText != "")
		$returnVar .= ',"text": "'.$inlineText.'"';
	if($tagPrint != "")
		$returnVar .= ',"tags": '.$tagPrint;
	$returnVar .='}';
	
	return $returnVar;
}

?>


