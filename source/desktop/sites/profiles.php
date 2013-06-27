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
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/profiles/profiles.dk.php');
			break;
		case 'en':
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/profiles/profiles.en.php');
			break;
	}

					
	$content = '';
	$listinfo = db_getProfiles();
	$listOfUsersAvailable = array();
	$ids = array();
	foreach ($listinfo as $user){
		array_push($ids,$user["id"]);
	}
	$listOfUsersAvailable = db_getProfileInfoMultiple($ids);

	foreach ($listOfUsersAvailable as $person) {
		if ($person["role"] == 0 ){
			$numberOfChilds = count($person["guardian_of"]);
			$content .= '
			<tr>
				<td rowspan="'.$numberOfChilds.'">'.$person["name"].'</td>';
			foreach ($person["guardian_of"] as $childOuter) {
				$childs = 0;
				foreach ($listOfUsersAvailable as $childInner) {
					if ($childInner["id"] == $childOuter) {
						$childs ++;
						if ($childs > 0) {
							$content .= '
							<tr>';
						}
						$content .= '
						<td>'.$childInner["name"].'</td>';
						$parentToEcho = array();
						foreach ($listOfUsersAvailable as $parent) {
							foreach ($parent["guardian_of"] as $key) {
								if ($parent["role"] == 1 && $key == $childOuter) {
									$parentEchoArray = [$parent["id"],$parent["name"]];
									array_push($parentToEcho, $parentEchoArray);
								}
							}
						}
						$content .= '
						<td>';
						foreach ($parentToEcho as $parentEcho) {
							$content .= '<a href="#ownProfile/user='.$parentEcho[0].'">'.$parentEcho[1].';';
						}
						$content .= '</td>';
						if ($childs > 0) {
							$content .= '
							</tr>';
						}
					}
				}
			}
			$content .= '
			</tr>';
		}
	}
?>
<html lang="en">
<head>
	<!-- Title -->
	<?php echo '<title>'.$PROFILES_STRINGS["headerTitle"].'</title>' ?>

	<!-- Stylesheets -->

	<!-- JavaScript -->

</head>

<body>
	<div class="container-fluid">
		<?php echo '<div class="breadcrump">'.$PROFILES_STRINGS["breadCrump"].'</div>' ?>

		<table class=" table table-bordered table-striped profiles-table" align=center>
			<thead>
				<tr>
					<th><?php echo $PROFILES_STRINGS["tblpedagogue"] ?></th>
					<th><?php echo $PROFILES_STRINGS["tblchildren"] ?></th>
					<th><?php echo $PROFILES_STRINGS["tblparents"] ?></th>
				</tr>
			</thead>
			<tbody>
				<?php echo $content; ?>
<!-- 				<tr>
					<td rowspan="2">Patrick Kronsmed</td>
					<td>Tommi Guldmund</td>
					<td>Hans Guldmund,<br>Heidi Guldmund</td>
					<tr>
						<td>Nicklas Jørgensen</td>
						<td>Mikkel Jørgensen,<br> Maiken Jørgensen</td>
					</tr>
				</tr>
				<tr>
					<td rowspan="1">Anne Nielsen</td>
					<td>Louis Mikkelsen</td>
					<td>John Mikkelsen,<br>Lene Mikkelsen</td>
				</tr>
				<tr>
					<td rowspan="1">Mette Guldbrand</td>
					<td>Tobeias Olesen</td>
					<td>Nicolaj Olesen,<br>Ida Olesen</td>
				</tr>
				<tr>
					<td rowspan="1">Mette Guldbrand</td>
					<td>Tobeias Olesen</td>
					<td>Nicolaj Olesen,<br>Ida Olesen</td>
				</tr>
				<tr>
					<td rowspan="2">Mette Guldbrand</td>
					<td>Tommi Guldmund</td>
					<td >Hans Guldmund,<br>Heidi Guldmund</td>
					<tr>
						<td>Nicklas Jørgensen</td>
						<td>Mikkel Jørgensen,<br> Maiken Jørgensen</td>
					</tr>
				</tr>
				<tr>
					<td rowspan="1">Mette Guldbrand</td>
					<td>Tobeias Olesen</td>
					<td>Nicolaj Olesen,<br>Ida Olesen</td>
				</tr> -->
			</tbody>
		</table>
		
		<br>
		<div align="center">
			<button class="btn btn-mini" type="button"><i class="icon-wrench" ></i> <?php echo $PROFILES_STRINGS["btnNewProfile"] ?></button>
		</div>
	</div>
</body>
</html>
