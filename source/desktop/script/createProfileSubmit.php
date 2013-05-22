<?php 
	if( ! $_SESSION)
	{
		session_start();
	}

	$_POST['profile'];
	$_POST['name'];
	$_POST['address'];
	$_POST['city'];
	$_POST['zipcode'];
	$_POST['phone'];
	$_POST['mobile'];
	$_POST['email'];
	$profileRole = 0;
	switch ($_POST['profile']) {
		case '1':
			$profileRole = 2;
			break;
		case '2':
			$profileRole = 1;
			break;
		case '3':
			$profileRole = 0;
			break;
	}

	function error($error){
		header('Location: /#createProfile/e='.$error);
		exit;
	}
	
	isset($_POST['name']) or error("1");
	
	if($_POST['profile'] != "1"){
		isset($_POST['email']) or error("2");
	}
	
	//DB call start

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

	// Create profile
	$buffer = '{
		"action": "create",
	    "auth": {
	        "username": "'.$_SESSION['username'].'",
	        "password": "'.$_SESSION['password'].'"
	    },
	    "data": { 
  			"type": "profile", 
  			"values": [{
  				  "name": 		"'.$_POST['name'].'", 
				  "email": 		"'.$_POST['email'].'", 
				  "department": '.$_SESSION['department'].', 
				  "role": 		'.$profileRole.', 
				  "address": 	"'.$_POST['address'].', '.$_POST['zipcode'].', '.$_POST['city'].'", 
				  "phone": 		"'.$_POST['phone'].'"
  			}]
		}
	}';


	$qr = generateNewQr();
	$username = explode(" ", $_POST['name']);

	$return = db_query($buffer);
	// echo "ID på oprettet profil: ". $return["data"][0]."<br>";

	if ($profileRole == 2){
		$buffer = '{
			"action": "create",
		    "auth": {
		        "username": "'.$_SESSION['username'].'",
		        "password": "'.$_SESSION['password'].'"
		    },
		    "data": { 
  			"type": "user", 
  				"values": [{
					  "username": "'.$username[0].'",
					  "profile": '.$return["data"][0].', 
					  "certificate": "'.$qr.'"
  				}]
			}
		}';
	}
	else{
		$passWord = substr(md5(time()),0,10);
		// echo 'Password til nyoprettet bruger : '. $passWord .'<br>';
		$buffer = '{
			"action": "create",
		    "auth": {
		        "username": "'.$_SESSION['username'].'",
		        "password": "'.$_SESSION['password'].'"
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
	}

	db_query($buffer);
	// echo "ID på oprettet user: ". $return["data"][0]."<br>";

	//DB call end
	
	header('Location: /#createProfile/e=0');
?>