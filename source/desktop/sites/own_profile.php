<!DOCTYPE HTML>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- Force document mode to IE9 standards -->
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
	session_start();
	if (isset($_SESSION['lang'])) {$lang = $_SESSION['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/own_profile/own_profile.dk.php');
			echo '<script src="assets/lang/own_profile_js/own_profile_js.dk.js"></script>';
			break;
		case 'en':
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/own_profile/own_profile.en.php');
			echo '<script src="assets/lang/own_profile_js/own_profile_js.en.js"></script>';
			break;
	}
	//Include special profiles file
	include "../include/profiles.php";

	require_once($_SERVER['DOCUMENT_ROOT']."/db/db.php");
	$userName = $_SESSION['username'];
	$result = $connection->query("SELECT * FROM Profile WHERE idProfile = '$userName' ");
	if ($result->num_rows > 0)
	{
		$row = $result->fetch_assoc();
	}
	if (isset($_GET["action"])) {$action = $_GET["action"];} else {$action = '';}
	$title = getTitleFromAction($action);
	$content = getContentFromAction($action);


echo '
<html lang="en">
<head>
	'.$title.'

	<!-- JavaScript -->
	<script src="assets/js/profileEdit.js"></script>
	<script src="assets/js/print.js"></script>
	
	<!--- Picture Uploading Scripts --->
	<link rel="stylesheet" type="text/css" href="include/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" />
	<script type="text/javascript" src="include/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>

</head>

<body>
	<div class="container-fluid">
		'.$content.'
	</div>
</body>
<iframe name="print_frame" id="print_frame" width="0" height="0" frameborder="0" src="about:blank"></iframe>
</html>
';

function getTitleFromAction($action)
{
	switch ($action) {
		case '':
		case 'pedagogue':
			return '<title>'.$PROFILE_STRINGS["headerTitle"].'</title>';
		case 'child':
			return '<title>'.$PROFILE_STRINGS["headerTitle"].'</title>';
		case 'department':
			return '<title>'.$DEPARTMENT_STRINGS["headerTitle"].'</title>';
		default:
			return '<title>'.$PROFILE_STRINGS["headerTitle"].'</title>';
	}
}

function getContentFromAction($action)
{
	switch ($action) {
		case '':
		case 'pedagogue':
			return pedagogue();
		case 'child':
			return child();
		case 'department':
			return department();
		default:
			return pedagogue();
	}
}


function pedagogue()
{
	global $PROFILE_STRINGS;
	$content = '
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
							<td><button class="btn btn-mini btnEditChild" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
						<tr>
							<td>Hans Jensen</td>
							<td>Anne Larsen; Peter Svendsen</td>
							<td><button class="btn btn-mini btnEditChild" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td>Gerta Larsen; Ken Larsen</td>
							<td><button class="btn btn-mini btnEditChild" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>Mai Petersen; Hans Jensen</td>
							<td><button class="btn btn-mini btnEditChild" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="span6">
				<div>
					<img class="profile_picture" src="../assets/img/girafAdminLogo-01.svg">
					<button class="btn profile-btn" type="button" onclick="changeProfilePicturePopup()"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["pictureEdit"].'</button>
				</div>
				<div class="profile-security">
					<p class="lead text-center" id="security">'.$PROFILE_STRINGS["security"].'</p>
					<div class="profile-qr">
						<label>'.$PROFILE_STRINGS["changePassword"].'</label>
						<form>
							<input id="input-password" type="password" placeholder="'.$PROFILE_STRINGS["placeholderPassword"].'"><br>
							<input id="input-password" type="password" placeholder="'.$PROFILE_STRINGS["placeholderRepeatPassword"].'"><br>
							<input class="btn btn-primary" type="submit" value="'.$PROFILE_STRINGS["changePasswordSubmit"].'">
						</form>
						</br>
						<button class="btn profile-btn" id="btn-gen" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["qrGenerateNewAndPrint"].'</button>
					</div>
					<div class="profile-qr-status"> </div>
				</div>
			</div>
		</div>
	';
	return $content;
}


function child()
{
	global $PROFILE_STRINGS;
	$content = '
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
							<td>'.$PROFILE_STRINGS["mother"].'</td>
							<td>29 69 59 49</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["father"].'</td>
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
					<h3>'.$PROFILE_STRINGS["tbParents"].'</h3>
					<table class="table table-striped">
						<tr>
							<th>'.$PROFILE_STRINGS["tblName"].'</th>
							<th>'.$PROFILE_STRINGS["tblEditChild"].'</th>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
					</table>
					<h3>'.$PROFILE_STRINGS["h_pedagogues"].'</h3>
					<table class="table table-striped">
						<tr>
							<th>'.$PROFILE_STRINGS["tblName"].'</th>
							<th>'.$PROFILE_STRINGS["tblEditRelation"].'</th>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="span6">
				<div>
					<img class="profile_picture" src="../assets/img/girafAdminLogo-01.svg">
					<button class="btn profile-btn" type="button" onclick="changeProfilePicturePopup()"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["pictureEdit"].'</button>
				</div>
				<div class="profile-security">
					<p class="lead text-center" id="security">'.$PROFILE_STRINGS["security"].'</p>
					<div class="profile-qr">
						<label>'.$PROFILE_STRINGS["changePassword"].'</label>
						<form>
							<input type="text" placeholder="'.$PROFILE_STRINGS["placeholderPassword"].'"><br>
							<input type="text" placeholder="'.$PROFILE_STRINGS["placeholderRepeatPassword"].'"><br>
							<input class="btn btn-primary" type="submit" value="'.$PROFILE_STRINGS["changePasswordSubmit"].'">
						</form>
						</br>
						<button class="btn profile-btn" id="btn-gen" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["qrGenerateNewAndPrint"].'</button>
					</div>
					<div class="profile-qr-status"> </div>
				</div>
			</div>
		</div>
	';
	return $content;
}


function department()
{
	global $DEPARTMENT_STRINGS;
	$content = '
	<div class="breadcrump">'.$DEPARTMENT_STRINGS["breadCrump"].'</div>
		<div class="row">
			<div class="span6">
				<div class="container-fluid">
					<h3>'.$DEPARTMENT_STRINGS["h_personalInfo"].'</h3>
					<table class="table table-profile">
						<tr>
							<td>'.$DEPARTMENT_STRINGS["name"].'</td>
							<td>Birken</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$DEPARTMENT_STRINGS["phoneNr"].'</td>
							<td>29 69 59 70</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$DEPARTMENT_STRINGS["mobilePhoneNr"].'</td>
							<td>99 69 59 80</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$DEPARTMENT_STRINGS["address"].'</td>
							<td>Birkegade 1, 9000 Aalborg</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>'.$DEPARTMENT_STRINGS["dpHead"].'</td>
							<td>Hans Jensen</td>
							<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
					</table>
					<h3>'.$DEPARTMENT_STRINGS["h_pedagogues"].'</h3>
					<table class="table table-striped">
						<tr>
							<th>'.$DEPARTMENT_STRINGS["tblName"].'</th>
							<th>'.$DEPARTMENT_STRINGS["tblAttachedChildren"].'</th>
							<th>'.$DEPARTMENT_STRINGS["tblEditPedagogue"].'</th>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>Mai Petersen; Hans Jensen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>Hans Jensen</td>
							<td>Anne Larsen; Peter Svendsen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td>Gerta Larsen; Ken Larsen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>Mai Petersen; Hans Jensen</td>
							<td><button class="btn btn-mini" type="button"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["btnEdit"].'</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="span6">
				<div>
					<img class="profile_picture" src="../assets/img/randomHouse.svg">
					<button class="btn profile-btn" type="button" onclick="changeProfilePicturePopup()"><i class="icon-wrench"></i>'.$DEPARTMENT_STRINGS["pictureEdit"].'</button>
				</div>
			</div>
		</div>
	';
	return $content;
}