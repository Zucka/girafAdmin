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
?>
	<html lang="en">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- Force document mode to IE9 standards -->
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
	
	<!--- Picture Uploading Scripts --->
	<link rel="stylesheet" type="text/css" href="jquery.imgareaselect-0.9.8/css/imgareaselect-default.css" />
	<script type="text/javascript" src="jquery.imgareaselect-0.9.8/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="jquery.imgareaselect-0.9.8/scripts/jquery.imgareaselect.pack.js"></script>
	
	
	<link rel="shortcut icon" href="../assets/ico/favicon.ico">