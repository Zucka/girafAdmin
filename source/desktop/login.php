<?php
	session_start();
	require_once('db/db.php');
	if (isset($_GET['action'])) {$action = $_GET['action'];} else {$action = '';}
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
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
	

	
	if ($action == '')
	{
		if (isset($_GET['error'])) {$error = $_GET['error'];} else {$error = '';}
		$errorStartUsername = ''; $errorEndUsername = ''; $errorStartPassword = ''; $errorEndPassword = '';
		if ($error == '1')
		{
			$errorStartUsername = '	<div class="control-group error">
										<div class="controls">
								';
			$errorEndUsername	= '			<span class="help-inline">'.$LOGIN_STRINGS["errorUsernameNotFound"].'</span>
										</div> <!-- controls -->
									</div> <!-- control-group error -->
								';
		}
		elseif ($error == '2')
		{
			$errorStartPassword = '	<div class="control-group error">
										<div class="controls">
								';
			$errorEndPassword	= '			<span class="help-inline">'.$LOGIN_STRINGS["errorWrongPassword"].'</span>
										</div> <!-- controls -->
									</div> <!-- control-group error -->
								';
		}
		echo '
				<html lang="en">
					<head>
				    	<meta charset="utf-8">
				    	<title>'.$LOGIN_STRINGS["headerTitle"].'</title>
				    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
				    	<meta name="description" content="">
				    	<meta name="author" content="">

				    	<!-- Stylesheets -->
				    	<link href="assets/css/bootstrap.css" rel="stylesheet">
				    	<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
				    	<link href="assets/css/style.css" rel="stylesheet">

				    	<!-- Fav and touch icons -->
						<link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
						<link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
						<link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
						<link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
						<link rel="shortcut icon" href="../assets/ico/favicon.png">
					</head>
					<body>
					<div class="container">
						<form class="form-signin" action="?action=login" method="post">
							<img class="logo" src="assets/img/girafAdminLogo-01.svg" width="104" height="142">
							'.$errorStartUsername.'
							<input type="text" name="username" class="input-block-level" placeholder="'.$LOGIN_STRINGS["formUsername"].'">
							'.$errorEndUsername.'
							'.$errorStartPassword.'
							<input type="password" name="password" class="input-block-level" placeholder="'.$LOGIN_STRINGS["formPassword"].'">
							'.$errorEndPassword.'
							<!-- Outcommented until implementation
							<label class="checkbox">
								<input type="checkbox" value="remember-me"> '.$LOGIN_STRINGS["formRememberMe"].'
							</label> -->
							<button class="btn btn-large btn-primary" type="submit">'.$LOGIN_STRINGS["formSignIn"].'</button>
						</form>
					</div> <!-- /container -->

					<script src="assets/js/jquery.min.js"></script>
					<script src="assets/js/bootstrap.min.js"></script>
					</body>
				</html>
		';
	}
	elseif ($action == 'login') {
		if (isset($_POST['username'])) {$username = $connection->real_escape_string($_POST['username']);} else {header('location:login.php');}
		if (isset($_POST['password'])) {$password = $connection->real_escape_string($_POST['password']);} else {header('location:login.php');}

		$result = $connection->query("SELECT * FROM authusers WHERE username='".$username."' LIMIT 1");
		if ($result->num_rows == 0)
		{
			header('location:login.php?error=1'); //Error: Username not found
		}
		else
		{
			$row = $result->fetch_assoc();
			if ($row['password'] == sha1($password))
			{
				//Login success!
				$_SESSION['session_id'] = session_id();
				$_SESSION['username'] = $row['username'];
				session_write_close();
				header('location:index.php');
			}
			else
			{
				header('location:login.php?error=2'); //Error: Password was wrong
			}
		}
	}
?>