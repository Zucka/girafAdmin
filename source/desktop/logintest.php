<?
	require_once "db/new.db.php";
/*
function generateNewQr()
{
	$qr = "";
	for ($i=0; $i < 4; $i++) { 
		$time = microtime();
		$qr .= hash("sha512",$time);
		usleep(100); // sleep for 100 microseconds (0.1 milliseconds) to get a different time from microtime
	}
	return $qr;
}

	$name = "Kols koo";
	$passWord = substr(md5(time()),0,10);
	$qr = generateNewQr();
	echo $passWord .'<br>';
	// Create profile
	$buffer = '{
		"action": "create",
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": { 
  			"type": "profile", 
  			"values": [{
  				  "name": "'.$name.'", 
				  "email": "kolo@garif.dk", 
				  "department": 1, 
				  "role": 2, 
				  "address": "Hulavej 21", 
				  "phone": "22334455"
  			}]
		}
	}';

	$return = db_query($buffer);
	$username = explode(" ", $name);
	print_r($return);
	echo "</br>";
	echo "ID på oprettet profil: ". $return["data"][0]."<br>";

		$buffer = '{
		"action": "create",
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": { 
  			"type": "user", 
  			"values": [{
				  "username": "'.$username[0].'",
				  "profile": '.$return["data"][0].', 
				  "password": "'.$passWord.'",
				  "certificate": "'.$qr.'"
  			}]
		}
	}';

	$return = db_query($buffer);

	print_r($return);
	echo "</br>";
	echo "ID på oprettet user: <br>". $return["data"][0]."<br>";
*/


	// GET USER INFO (ROLE AND ID)
	$buffer = '{
		"action": null,
	    "auth": {
	        "username": "Neo",
	        "password": "8a98a0ecd5"
	    },
	    "data": "null"
	}';

	$return = db_query($buffer);
	echo $return["session"]["profile"];
	echo "</br>";

	// GET PROFILE

	$profile = $return["session"]["profile"];

	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "Neo",
	        "password": "8a98a0ecd5"
	    },
	    "data": {
	    	"type":"profile",
	    	"view":"details",
	    	"ids":['.$profile.']
	    }
	}';
	$profileInfo = db_query($buffer)["data"];

	echo $profileInfo[0]["name"];
		echo "</br>";
	echo $profileInfo[0]["phone"];
		echo "</br>";
	echo $profileInfo[0]["address"];
		echo "</br>";
	echo $profileInfo[0]["picture"];
	echo "</br>";

	// GET DEPARTMENT

	$guardian_of = $profileInfo[0]["guardian_of"][0];
	$department = $profileInfo[0]["department"];

	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "Leo",
	        "password": "c360007aed"
	    },
	    "data": {
	    	"type":"department",
	    	"view":"details",
	    	"ids":['.$department.']
	    }
	}';
	$departmentInfoe = db_query($buffer)["data"];
	print_r($departmentInfoe);
	echo $departmentInfoe[0]["name"];
		echo "</br>";
	echo $departmentInfoe[0]["phone"];
		echo "</br>";
	echo $departmentInfoe[0]["address"];
		echo "</br>";
	echo $departmentInfoe[0]["email"];
	echo "</br>";

	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "Leo",
	        "password": "c360007aed"
	    },
	    "data": {
	    	"type":"department",
	    	"view":"list",
	    	"ids":null
	    }
	}';
	$departmentInfoe = db_query($buffer)["data"];
	print_r($departmentInfoe);
	echo "hej<br>";
	// GET GUARDIAN OF
/*
	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": {
	    	"type":"profile",
	    	"view":"details",
	    	"ids":['.$guardian_of.']
	    }
	}';
	$guardian_ofInfo = db_query($buffer)["data"];

	echo $guardian_ofInfo[0]["name"];
		echo "</br>";
	echo $guardian_ofInfo[0]["phone"];
		echo "</br>";
	echo $guardian_ofInfo[0]["address"];
		echo "</br>";
	echo $guardian_ofInfo[0]["email"];
	echo "</br>";

	$child1 = db_getProfiles($guardian_of);

*/
?>



