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
	session_start();
	require_once($_SERVER['DOCUMENT_ROOT']."/db/new.db.php");
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

	if (isset($_GET["action"])) {$action = $_GET["action"];} else {$action = '';}
	if (isset($_GET["user"])) {$viewingUserId = $_GET["user"];} else {$viewingUserId = '';}

	if ($viewingUserId !='') {
		$profileInfo = db_getProfileInfo($viewingUserId);
	}
	else {
		$profileInfo = db_getProfileInfo($_SESSION["userId"]);
	}
	$departmentInfo = db_getDepartmentInfo($profileInfo[0]["department"]);

	if ($action == '') {
		switch ($profileInfo[0]["role"]) {
			case '0':
				$action = 'pedagogue';
				break;
			case '1':
				$action = 'pedagogue';
				break;
			case '2':
				$action = 'child';
				break;
			case '3':
				$action = 'department';
				break;			
			default:
				# code...
				break;
		}
	}

	$title = getTitleFromAction($action);
	$btnEditVar = '<td><button class="btn btn-mini buttonEdit" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["btnEdit"].'</button></td>';
	$content = getContentFromAction($action);



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
?>
<html lang="en">
<head>
	<?php echo $title ?>

	<!-- JavaScript -->
	<script src="assets/js/profileEdit.js"></script>
	<script src="assets/js/print.js"></script>
	
	<!-- Picture Uploading Scripts -->
	<link rel="stylesheet" type="text/css" href="include/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" />
	<script type="text/javascript" src="include/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>

</head>

<body>
	<div class="container-fluid">
		</br>
		<?php echo $content ?>
	</div>
</body>
<iframe name="print_frame" id="print_frame" width="0" height="0" frameborder="0" src="about:blank"></iframe>
</html>
';

<?php
function contentStart($role){
	if ($role == 1 || $role == 0 || $role == 2){
			global $PROFILE_STRINGS;
	$contentStart = '
	<div class="breadcrump">'.$PROFILE_STRINGS["breadCrump"].'</div>
		<div class="row">
			<div class="span6">
				<div class="container-fluid">
					<h3>'.$PROFILE_STRINGS["h_personalInfo"].'</h3>
					';
	return $contentStart;}	
	elseif ($role == 4){
	global $DEPARTMENT_STRINGS;
	$contentStart = '
	<div class="breadcrump">'.$DEPARTMENT_STRINGS["breadCrump"].'</div>
		<div class="row">
			<div class="span6">
				<div class="container-fluid">
					<h3>'.$DEPARTMENT_STRINGS["h_personalInfo"].'</h3>
					';}
	return $contentStart;
}

function contentBlock2($role)
{
	global $PROFILE_STRINGS;
	global $profileInfo;
	global $departmentInfo;
	global $btnEditVar;
	$parents = "";
	$listinfo = db_getProfiles();
	$listOfUsersAvailable = array();
	foreach ($listinfo as $user) {
		array_push($listOfUsersAvailable, db_getProfileInfo($user["id"]));
	}

	if ($role == 0 || $role == 1 ){
		$attachedChildren = '
							<h3>'.$PROFILE_STRINGS["h_attachedChildren"].'</h3>
							<table class="table table-striped">
								<tr>
									<th>'.$PROFILE_STRINGS["tblName"].'</th>
									<th>'.$PROFILE_STRINGS["tbParents"].'</th>
									<th>'.$PROFILE_STRINGS["tblEditChild"].'</th>
								</tr>
							';
		foreach($profileInfo[0]["guardian_of"] as $guardian_of) {
			foreach ($listOfUsersAvailable as $user) {
					if ($user[0]["role"] == 1) {
						$parents .= $user[0]["name"].';';
					}
				}
				$guardian_ofInfo = db_getProfileInfo($guardian_of);
			
			$attachedChildren .= '
								<tr>
									<td>'.$guardian_ofInfo[0]["name"].'</td>
									<td>'.$parents.'</td>
									<td><button class="btn btn-mini btnEditChild" type="button"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["tblEditProfile"].'</button></td>
								</tr>';}
			$attachedChildren .= '
								</table>
								';

		return $attachedChildren;
	}
	elseif ($role == 2){
		$contentBlock2 = '
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
					';
		return $contentBlock2;
	}
}

function contentBlock3()
{
	$contentBlock3 ='
					<div>
						<img class="profile_picture" src="data:image/jpeg;base64{'.$profileInfo[0]["picture"].'}">
						<button class="btn profile-btn" type="button" onclick="changeProfilePicturePopup()"><i class="icon-wrench"></i>'.$PROFILE_STRINGS["pictureEdit"].'</button>
					</div>
					';
	return $contentBlock3;
}

function contentPersonalInfo($role)
{
	global $PROFILE_STRINGS;
	global $profileInfo;
	global $departmentInfo;
	global $btnEditVar;
	if ($role == 0 || $role == 1 ){
	$contentStart = '
					<table class="table table-profile">
						<tr>
							<td>'.$PROFILE_STRINGS["name"].'</td>
							<td>'.$profileInfo[0]["name"].'</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["phoneNr"].'</td>
							<td>'.$profileInfo[0]["phone"].'</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["address"].'</td>
							<td>'.$profileInfo[0]["address"].'</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["department"].'</td>
							<td>'.$departmentInfo[0]["name"].'</td>
							'.$btnEditVar.'
						</tr>
					</table>
					';}
	elseif ($role == 2){
	$contentStart = '
					<table class="table table-profile">
						<tr>
							<td>'.$PROFILE_STRINGS["name"].'</td>
							<td>'.$profileInfo[0]["name"].'</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["mother"].'</td>
							<td>29 69 59 49</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["father"].'</td>
							<td>99 69 59 49</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["address"].'</td>
							<td>'.$profileInfo[0]["address"].'</td>
							'.$btnEditVar.'
						</tr>
						<tr>
							<td>'.$PROFILE_STRINGS["department"].'</td>
							<td>'.$departmentInfo[0]["name"].'</td>
							'.$btnEditVar.'
						</tr>
					</table>
					';}
	return $contentStart;
}

function pedagogue()
{
	global $PROFILE_STRINGS;
	global $profileInfo;
	global $departmentInfo;
	$contentStart = contentStart(1);
	$contentBlock1 = contentPersonalInfo(1);
	$contentBlock2 = contentBlock2(1);
	$contentBlock3 = contentBlock3(1);
	$content = '
	'.$contentStart.'
	'.$contentBlock1.'
	'.$contentBlock2.'
				</div>
			</div>
			<div class="span6">
			'.$contentBlock3.'
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
	global $profileInfo;
	global $departmentInfo;
	$contentBlock2 = contentBlock2(2);
	$contentBlock3 = contentBlock3(2);
	$contentStart = contentStart(2);
	$contentBlock1 = contentPersonalInfo(2);
	$content = '
	'.$contentStart.'
	'.$contentBlock1.'
	'.$contentBlock2.'
				</div>
			</div>
			<div class="span6">
			'.$contentBlock3.'
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
?>