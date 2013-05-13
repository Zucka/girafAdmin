<?php
require "db/new.db.php";

//Run function
$ProfilBillede = makeJsonProfilePic("2","tudtudImage");
echo "ProfilBillede: ".$ProfilBillede;
db_uploadeProfilePic($ProfilBillede);

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
	    	"values":['.$profileImage.']
	    }
	}';
	echo "<br><br>Data: ".$data;
	$result = db_query($data);
	echo "<br><br>Result: ".$result['status'];
	echo "<br><br>Error :".$result['errors'][0];
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
	return
	'{ 
		"id": '.$id.', 
		"value": { 
			"id": '.$id.', 
			"picture": "'.base64_encode($profileImage).'"
			}
	}';
}

	
	
?>