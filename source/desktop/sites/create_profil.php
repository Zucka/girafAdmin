<?php
	session_start();
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}

	switch ($lang) {
		case 'en':
			include('assets/lang/login/login.en.php');
			break;
		case 'dk':
			include('assets/lang/login/login.dk.php');
			break;
		default:
			include('assets/lang/login/login.en.php');
			break;
	}
	
	?>