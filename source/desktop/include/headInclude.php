<?php
	//This file includes every important script, or other file in the headder.
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include('assets/lang/navigation/navigation.en.php');
			break;
		case 'dk':
			include('assets/lang/navigation/navigation.dk.php');
			break;
		default:
			include('assets/lang/navigation/navigation.en.php');
			break;
	}
	//Parse the $_POST array onto the navigation.js script
	echo "<script>
			var postData = ";
			echo json_encode($_POST);
	echo "</script>";
	
	if(isset($_FILES['newProfilePic']['tmp_name'])){
		//Call croping and upload script
		require "script/profilePicUpload.php";
	}
?>
	<html lang="en">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- Force document mode to IE9 standards -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="GIRAF">
	
	<!-- Fav and touch icons -->
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
	<link rel="shortcut icon" href="../assets/ico/favicon.ico">
	
	
	<!-- Style Sheets -->
	<link href="assets/css/bootstrap.css" rel="stylesheet">
	<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="assets/css/style.css" rel="stylesheet">
	
	<!-- JavaScript -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/bootstrap.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	
	<script src="assets/js/navigation.js"></script>
	<script src="assets/js/modal.js"></script>
	
	