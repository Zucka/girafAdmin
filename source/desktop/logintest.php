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
	$return = db_query($buffer);

	echo $return["data"][0]["name"];
		echo "</br>";
	echo $return["data"][0]["department"];
		echo "</br>";
	echo $return["data"][0]["guardian_of"][0];
		echo "</br>";
	echo $return["data"][0]["name"];
	echo "</br>";

	// GET DEPARTMENT

	$guardian_of = $return["data"][0]["guardian_of"][0];
	$department = $return["data"][0]["department"];

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
	$return = db_query($buffer);

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

	$return = db_query($buffer);




?>



