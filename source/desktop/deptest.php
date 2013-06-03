<?
	require_once "db/new.db.php";

	// GET DEPARTMENT

	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": {
	    	"type":"department",
	    	"view":"details",
	    	"ids":[1]
	    }
	}';


	$departmentInfoe = db_query($buffer);
	print_r($departmentInfoe);
	$test = mb_convert_encoding($departmentInfoe, "ISO-8859-1");
	print_r($test);
	// 	echo "</br>";
	// echo $departmentInfoe["phone"];
	// 	echo "</br>";
	// echo $departmentInfoe[0]["address"];
	// 	echo "</br>";
	// echo $departmentInfoe[0]["email"];
	// echo "</br>";
?>



