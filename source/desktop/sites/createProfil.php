<?php
	session_start();
	if (isset($_GET['action'])) {$action = $_GET['action'];} else {$action = '';}
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include('assets/lang/create_profile/create_profile.en.php');
			break;
		case 'dk':
			include('assets/lang/create_profile/create_profile.dk.php');
			break;
		default:
			include('assets/lang/create_profile/create_profile.en.php');
			break;
	}
	
	if ($action == ''){
	echo '
		<html lang="en">
		<head>
				<meta charset="utf-8">
				    	<title>'.$CREATEPROFILE_STRINGS["headerTitle"].'</title>
				    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
				    	<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- Force document mode to IE9 standards -->
				    	<meta name="description" content="">
				    	<meta name="author" content="">

				    	<!-- Stylesheets -->
				    	<link href="assets/css/bootstrap.css" rel="stylesheet">
				    	<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
				    	<link href="assets/css/style.css" rel="stylesheet">
						
						<!--JavaScript-->
						<link href="script/create_profile.php" rel="datavalidation">

				    	<!-- Fav and touch icons -->
						<link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
						<link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
						<link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
						<link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
						<link rel="shortcut icon" href="../assets/ico/favicon.ico">
					</head>
					<body>
						<div align="center">
		<h1>'.$CREATEPROFILE_STRINGS["headline"].'</h1>
	</div>
	
	<form class="form-signin" action="createProfile.php>
			Profil: 
			<select name="profile" id="profile">
				<option>'.$CREATEPROFILE_STRINGS["child"].'</option>
				<option>'.$CREATEPROFILE_STRINGS["parant"].'</option>
				<option>'.$CREATEPROFILE_STRINGS["guardian"].'</option>
			</select><br>
			'.$CREATEPROFILE_STRINGS["name"].':<br>
			<input type="text" placeholder="Type something…"><br>
			'.$CREATEPROFILE_STRINGS["address"].':<br>
			<input type="text" placeholder=""><br>
			'.$CREATEPROFILE_STRINGS["city"].': <br>
			<input type="text" onkeyup="zipCode(this.value)"><br>
			'.$CREATEPROFILE_STRINGS["zipcode"].':<br>
			<input class="span1" type="text" placeholder=""><br>
			'.$CREATEPROFILE_STRINGS["phone"].':<br>
			<input type="text" placeholder=""><br>
			'.$CREATEPROFILE_STRINGS["mobile"].':<br>
			<input type="text" placeholder=""><br>´'
			if( .$CREATEPROFILE_STRINGS["parant"]. == $_POST["fname"]){
			echo' '.$CREATEPROFILE_STRINGS["email"].':<br><input type="email" placeholder=""><br>'}
			echo'
	</form>
		<br>
		<div align="center">
		<button class="btn btn-large" type="button"><i class="icon-user" ></i> '.$CREATEPROFILE_STRINGS["createprofile"].'</button>
		</div>

					<script src="assets/js/jquery.min.js"></script>
					<script src="assets/js/bootstrap.min.js"></script>
					</body>
				</html>
		';
	}
	}
	
	
?>
