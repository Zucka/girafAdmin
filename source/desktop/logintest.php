<?
	require_once "db/new.db.php";

	// GET USER INFO (ROLE AND ID)
	$buffer = '{
		"action": null,
	    "auth": {
	        "username": "John",
	        "password": "123456"
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
	        "username": "John",
	        "password": "123456"
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
	echo $profileInfo[0]["email"];
	echo "</br>";

	// GET DEPARTMENT

	$guardian_of = $profileInfo[0]["guardian_of"][0];
	$department = $profileInfo[0]["department"];

	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": {
	    	"type":"department",
	    	"view":"details",
	    	"ids":['.$department.']
	    }
	}';
	$departmentInfoe = db_query($buffer)["data"];
	echo $departmentInfoe;
	echo $departmentInfoe[0]["name"];
		echo "</br>";
	echo $departmentInfoe[0]["phone"];
		echo "</br>";
	echo $departmentInfoe[0]["address"];
		echo "</br>";
	echo $departmentInfoe[0]["email"];
	echo "</br>";

	// GET GUARDIAN OF

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


?>



