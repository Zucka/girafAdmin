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
	var languageEmail = "<?php echo .$CREATEPROFILE_STRINGS["email"]; ?>";
	</script>
	
<?php
	echo '
		<html lang="en">
			<head>
				<meta charset="utf-8">
				<title>'.$CREATEPROFILE_STRINGS["headerTitle"].'</title>
				
				<!--JavaScript-->
				<script src="assets/js/create_profile.js"> </script>

			</head>
			<body>
				<div class="breadcrump">
					'.$CREATEPROFILE_STRINGS["headline"].'
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
