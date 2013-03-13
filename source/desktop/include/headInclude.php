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
	
	<!-- Style Sheets -->
	<link href="assets/css/bootstrap.css" rel="stylesheet">
	<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="assets/css/style.css" rel="stylesheet">
	
	<!-- JavaScript -->
	<script src="assets/js/bootstrap.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="assets/js/navigation.js"></script>
	
	<link rel="shortcut icon" href="../assets/ico/favicon.png">