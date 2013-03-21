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
		include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/qrmanager/qrmanager.en.php');
		break;
	case 'dk':
		include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/qrmanager/qrmanager.dk.php');
		break;
	default:
		include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/qrmanager/qrmanager.en.php');
		break;
}

require_once($_SERVER['DOCUMENT_ROOT']."/db/db.php");
$userName = $_SESSION['username'];
$result = $connection->query("SELECT * FROM Profile WHERE idProfile = '$userName' ");
if ($result->num_rows > 0)
{
	$row = $result->fetch_assoc();
}
if (isset($_GET["action"])) {$action = $_GET["action"];} else {$action = '';}
$content = getContentFromAction($action);
echo '
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>'.$QRMANGER_STRINGS["headerTitle"].'</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="GIRAF">

	<!-- Stylesheets -->
	<link href="../assets/css/bootstrap.css" rel="stylesheet">
	<link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="../assets/css/style.css" rel="stylesheet">
	<link href="tempstyle.css" rel="stylesheet">

	<!-- Fav and touch icons -->
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
	<link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
	<link rel="shortcut icon" href="../assets/ico/favicon.ico">

	<!-- JavaScript -->
</head>

<body>
	<div class="container-fluid">
		'.$content.'
	</div>


	<script src="../assets/js/jquery.min.js"></script>
	<script src="../assets/js/bootstrap.min.js"></script>
</body>
</html>
';

function getContentFromAction($action)
{
	switch ($action) {
		case '':
		case 'main':
			return mainContent();
		case 'edit':
			return editContent();
		case 'editSubmit':
			return editSubmitContent();
		default:
			return mainContent();
	}
}

function mainContent()
{
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpMain"].'</div>
	<div class="row">
		<div class="span3"> </div>
		<div class="span6">
			<div class="row">
				<button class="btn qrmanager-buttons">Ret QR på bruger</button>
			</div>
			<div class="row">
				<button class="btn qrmanager-buttons">Vælg QR til print</button>
			</div>
			<div class="row">
				<button class="btn qrmanager-buttons">Print Alle QR</button>
			</div>
		</div>
		<div class="span3"> </div>
	</div>
	';
	return $content;
}

function editContent()
{
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpEdit"].'</div>
	<div class="row">
		<div class="span4">
			<h4 class="text-center">Børn</h4>
			<table class="table table-bordered table-striped qrmanager-tableEdit">
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>	
			</table>
		</div>
		<div class="span4">
			<h4 class="text-center">Pædagog</h4>
			<table class="table table-bordered table-striped qrmanager-tableEdit">
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>	
			</table>
		</div>
		<div class="span4">
			<h4 class="text-center">Forældre</h4>
			<table class="table table-bordered table-striped qrmanager-tableEdit">
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><button class="btn btn-mini qrmanager-btnEdit"><i class="icon-wrench"></i>Ret</td>
				</tr>	
			</table>
		</div>
	</div>
	';

	return $content;
}

function editSubmitContent()
{
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpEditSubmit"].'</div>
	<div class="row">
		<div class="span12">
			<p class="lead text-center">Helly Hansen har fået en ny QR-kode, en mail er sendt til Helly Hansen om dette</p>
		</div>
	</div>
	<div class="row">
		<div class="span5"> </div>
		<div class="span4">
			<div class="qrmanager-qrcode">

			</div>
		</div>
		<div class="span3"> </div>
	</div>

	';

	return $content;
}
?>