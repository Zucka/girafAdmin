<!DOCTYPE HTML>
<!--
This file is part of GIRAF.

GIRAF is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

GIRAF is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with GIRAF.  If not, see <http://www.gnu.org/licenses/>.
-->
<?php

	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/own_profile/own_profile.en.php');
			break;
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/own_profile/own_profile.dk.php');
			break;
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/own_profile/own_profile.en.php');
			break;
	}

	require_once($_SERVER['DOCUMENT_ROOT']."/db/db.php");
	$userName = $_SESSION['username'];
	$result = $connection->query("SELECT * FROM Profile WHERE idProfile = '$userName' ");
	if ($result->num_rows > 0)
	{
		$row = $result->fetch_assoc();
	}
echo '
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>'.$PROFILE_STRINGS["headerTitle"].'</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="GIRAF">

	<!-- Stylesheets -->
	<link href="../assets/css/bootstrap.css" rel="stylesheet">
	<link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="../assets/css/style.css" rel="stylesheet">

	<!-- Fav and touch icons -->
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
	<link rel="shortcut icon" href="../assets/ico/favicon.ico">

	<!-- JavaScript -->
	<script src="assets/js/profileEdit.js"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="breadcrump">'.$PROFILE_STRINGS["breadCrump"].'</div>
		<div class="row">
			<div class="span6">
				<div class="container-fluid">
					<h3>'.$PROFILE_STRINGS["h_personalInfo"].'</h3>
					<table class="table table-profile">
						<tr>
							<td>'.$PROFILE_STRINGS["name"].'</td>
							<td>Jens Lauritsen</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["phoneNr"].'</td>
							<td>29 69 59 49</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["mobilePhoneNr"].'</td>
							<td>99 69 59 49</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["address"].'</td>
							<td>Bredgade 1, 9000 Aalborg</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["department"].'</td>
							<td>Bjælken</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
					</table>
					<h3>'.$PROFILE_STRINGS["h_attachedChildren"].'</h3>
					<table class="table table-striped">
						<tr>
							<th>'.$PROFILE_STRINGS["tblName"].'</th>
							<th>'.$PROFILE_STRINGS["tbParents"].'</th>
							<th>'.$PROFILE_STRINGS["tblEditChild"].'</th>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>Mai Petersen; Hans Jensen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
						<tr>
							<td>Hans Jensen</td>
							<td>Anne Larsen; Peter Svendsen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td>Gerta Larsen; Ken Larsen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>Mai Petersen; Hans Jensen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="span6">
				<div>
					<img class="profile_picture" src="../assets/img/girafAdminLogo-01.svg">
					<button class="btn profile-btn" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["pictureEdit"].'</button>
				</div>
				<div>
					<div class="profile-qr">
						<object data="../qrgen.php" type="image/svg+xml" />
					</div>
					<div class="profile-btn-qr">
						<button class="btn profile-btn" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["qrGenerateNew"].'</button>
						<button class="btn profile-btn" type="button"><i class="icon-print"></i>'.$PROFILE_STRINGS["qrPrint"].'</button>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="../assets/js/jquery.min.js"></script>
	<script src="../assets/js/bootstrap.min.js"></script>
</body>
</html>
';
?>


















