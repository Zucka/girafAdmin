<?php
	session_start();
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include('../assets/lang/create_profile/create_profile.en.php');
			break;
		case 'dk':
			include('assets/lang/create_profile/create_profile.dk.php');
			break;
		default:
			include('assets/lang/create_profile/create_profile.en.php');
			break;
	}
	
?>
	
	<script> 
	var languageEmail = "<?php echo $CREATEPROFILE_STRINGS["email"]; ?>";
	</script>
	
<?php
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
				<script src="assets/js/create_profile.js"> </script>

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
	
				<form class="form-signin" action="/#createProfile">
					<div class="profileType"> 
					Profil:
					<select name="profile" class="span5 profileInput"  id="profile" vertical-align="middle">
						<option value="1">'.$CREATEPROFILE_STRINGS["child"].'</option>
						<option value="2">'.$CREATEPROFILE_STRINGS["parant"].'</option>
						<option value="3">'.$CREATEPROFILE_STRINGS["guardian"].'</option>
					</select></div><br>
					'.$CREATEPROFILE_STRINGS["name"].':<br>
					<input class="profileInput" id="name" type="text" placeholder="" required>*<br>
					'.$CREATEPROFILE_STRINGS["address"].':<br>
					<input class="profileInput" type="text" placeholder=""><br>
					'.$CREATEPROFILE_STRINGS["city"].': <br>
					<input class="profileInput" type="text" id="inputWarning" onkeyup="zipCode(this.value)"><br>
					'.$CREATEPROFILE_STRINGS["zipcode"].':<br>
					<input class="profileInput" type="text" class="span4" type="text" placeholder=""><br>
					'.$CREATEPROFILE_STRINGS["phone"].':<br>
					<input class="profileInput" type="text" placeholder=""><br>
					'.$CREATEPROFILE_STRINGS["mobile"].':<br>
					<input class="profileInput" type="text"  placeholder=""><br>
					<div id="emailContainer"></div>
					<div color="#FF0000">'.$CREATEPROFILE_STRINGS["dataRequired"].'</div>
					
				</form>
				<br>
				<div align="center">
					<button class="btn btn-large" id="create" type="button" disabled><i class="icon-user"></i> '.$CREATEPROFILE_STRINGS["createprofile"].'</button>
				</div>

				<script src="assets/js/jquery.min.js"></script>
				<script src="assets/js/bootstrap.min.js"></script>
			</body>
		</html>
	';
?>
