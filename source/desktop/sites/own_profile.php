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
	//Include special profiles file
	include "../include/profiles.php";

	require_once($_SERVER['DOCUMENT_ROOT']."/db/db.php");
	$userName = $_SESSION['username'];
	$result = $connection->query("SELECT * FROM Profile WHERE idProfile = '$userName' ");
	if ($result->num_rows > 0)
	{
		$row = $result->fetch_assoc();
	}
echo '

	<title>'.$PROFILE_STRINGS["headerTitle"].'</title>

	<!-- JavaScript -->
	<script src="assets/js/profileEdit.js"></script>
	<script src="assets/js/print.js"></script>
	
	<!--- Picture Uploading Scripts --->
	<link rel="stylesheet" type="text/css" href="include/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" />
	<script type="text/javascript" src="include/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>

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
					<button class="btn profile-btn" type="button" onclick="changeProfilePicturePopup()"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["pictureEdit"].'</button>
				</div>
				<div>
					<div class="profile-qr">
						<object class="profile-qr-object" data="../qrgen.php" height="100%" width="100%" type="image/svg+xml"> </object>
					</div>
					<div class="profile-btn-qr">
						<button class="btn profile-btn" id="btn-gen" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["qrGenerateNew"].'</button>
						<button class="btn profile-btn" id="btn-print" type="button"><i class="icon-print"></i>'.$PROFILE_STRINGS["qrPrint"].'</button>
					</div>
					<div class="profile-qr-status"> </div>
				</div>
			</div>
		</div>
	</div>

	<iframe name="print_frame" id="print_frame" width="0" height="0" frameborder="0" src="about:blank"></iframe>
';

?>


















