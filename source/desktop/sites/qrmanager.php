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
require_once($_SERVER['DOCUMENT_ROOT']."/include/phpqrcode/qrlib.php");
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
	<title>'.$QRMANAGER_STRINGS["headerTitle"].'</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="GIRAF">

	<!-- Stylesheets -->
	<link href="../assets/css/bootstrap.css" rel="stylesheet">
	<link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="../assets/css/style.css" rel="stylesheet">
	<link href="../sites/tempstyle.css" rel="stylesheet">

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
		case 'choosePrint':
			return choosePrintContent();
		case 'choosePrintSubmit':
			return choosePrintSubmitContent();
		case 'printAll':
			return printAllContent();
		default:
			return mainContent();
	}
}

function mainContent()
{
	global $QRMANAGER_STRINGS;
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpMain"].'</div>
	<div class="row">
		<div class="span3"> </div>
		<div class="span6">
			<div class="row">
				<form action="index.php#qrManager/action=edit">
					<button class="btn qrmanager-buttons" type="submit">'.$QRMANAGER_STRINGS["mainButtonEditQr"].'</button>
				</form>
			</div>
			<div class="row">
				<form action="index.php#qrManager/action=choosePrint">
					<button class="btn qrmanager-buttons">'.$QRMANAGER_STRINGS["mainButtonChooseQr"].'</button>
				</form>
			</div>
			<div class="row">
				<form action="index.php#qrManager/action=printAll">
					<button class="btn qrmanager-buttons">'.$QRMANAGER_STRINGS["mainButtonPrintAllQr"].'</button>
				</form>
			</div>
		</div>
		<div class="span3"> </div>
	</div>
	';
	return $content;
}

function editContent()
{
	global $QRMANAGER_STRINGS;
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpEdit"].'</div>
	<div class="row">
		<div class="span1"></div>
		<div class="span3">
			<h4 class="text-center">'.$QRMANAGER_STRINGS["editQrChildren"].'</h4>
			<table class="table table-bordered table-striped qrmanager-table">
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>	
			</table>
		</div>
		<div class="span3">
			<h4 class="text-center">'.$QRMANAGER_STRINGS["editQrGuardians"].'</h4>
			<table class="table table-bordered table-striped qrmanager-table">
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>	
			</table>
		</div>
		<div class="span3">
			<h4 class="text-center">'.$QRMANAGER_STRINGS["editQrParents"].'</h4>
			<table class="table table-bordered table-striped qrmanager-table">
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td>
						<form class="qrManagerInlineForm" action="#qrManager/action=editSubmit">
							<button class="btn btn-mini qrmanager-btnEdit" type="submit"><i class="icon-wrench"></i>'.$QRMANAGER_STRINGS["editQrButtonEdit"].'</button>
						</form>
					</td>
				</tr>	
			</table>
		</div>
		<div class="span1"> </div>
	</div>
	';

	return $content;
}

function editSubmitContent()
{
	global $QRMANAGER_STRINGS;
	$leadInfoMessage = str_replace('%N','Helly Hansen',$QRMANAGER_STRINGS["editSubmitLeadInfoMessage"]);
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpEditSubmit"].'</div>
	<div class="row">
		<div class="span12">
			<p class="lead text-center">'.$leadInfoMessage.'</p>
		</div>
	</div>
	<div class="row">
		<div class="span5"> </div>
		<div class="span4">
			<div class="qrManagerQrCode">

			</div>
		</div>
		<div class="span3"> </div>
	</div>
	';

	return $content;
}

function choosePrintContent()
{
	global $QRMANAGER_STRINGS;
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpChoosePrint"].'</div>
	<div class="row">
		<div class="span12">
			<p class="lead text-center">'.$QRMANAGER_STRINGS["choosePrintLeadInfoMessage"].'</p>
		</div>
	</div>
	<form action="#qrManager/action=choosePrintSubmit" method="post">
	<div class="row">
		<div class="span1"></div>
		<div class="span3">
			<h4 class="text-center">'.$QRMANAGER_STRINGS["editQrChildren"].'</h4>
			<table class="table table-bordered table-striped qrmanager-table">
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
			</table>
		</div>
		<div class="span3">
			<h4 class="text-center">'.$QRMANAGER_STRINGS["editQrGuardians"].'</h4>
			<table class="table table-bordered table-striped qrmanager-table">
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
			</table>
		</div>
		<div class="span3">
			<h4 class="text-center">'.$QRMANAGER_STRINGS["editQrParents"].'</h4>
			<table class="table table-bordered table-striped qrmanager-table">
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
				<tr>
					<td>Helly Hansen</td>
					<td><input type="checkbox" name="ids[]" value="id here"></td>
				</tr>
			</table>
		</div>
		<div class="span1"></div>
	</div>
	<div class="row">
		<div class="span12">
			<input class="btn-primary btn-large btn-choosePrintSubmit" type="submit" value="'.$QRMANAGER_STRINGS["choosePrintSubmitText"].'">
		</div>
	</div>
	</form>
	';

	return $content;
}

function choosePrintSubmitContent()
{
	global $connection, $QRMANAGER_STRINGS;
	$content = '
	<div class="breadcrump">'.$QRMANAGER_STRINGS["breadCrumpChoosePrintSubmit"].'</div>
	<div class="row">
		<div class="span12">
			<p class="lead text-center">'.count($_POST['ids']).$QRMANAGER_STRINGS["choosePrintSubmitLeadInfoMessage"].'</p>
		</div>
	</div>
	';
	$query = "SELECT certificate FROM AuthUsers WHERE idUser IN (";
	foreach ($_POST['ids'] as $id) {
		$query .= $connection->real_escape_string($id).',';
	}
	$query = substr($query,0,strlen($query)-1);
	$query .= ')';
	$result = $connection->query($query);
	$i = 0;
	$inRow = false;
	if ($result->num_rows != 0)
	{
		while ($row = $result->fetch_assoc())
		{
			if ($i % 3 == 0)
			{
				if ($inRow)
				{
					$content .= '<div class="span1"></div></div>';
				}
				$content .= '<div class="row">\n <div class="span1"></div>';
				$inRow == true;
			}
			$content .= '
			<div class="span3">
				'.QRcode::svg($row['certificate'],false,4,4,false,0xFFFFFF,0x000000).'
			</div>
			';
		}
	}
	if ($inRow)
	{
		$content .= '<div class="span1"></div></div>';
	}
	$content .= '
	<div class="row">
		<button class="btn-primary btn-large btn-choosePrintSubmit">'.$QRMANAGER_STRINGS["choosePrintSubmitButtonSubmit"].'</button>
	</div>
	';

	return $content;
}

function printAllContent()
{
	global $QRMANAGER_STRINGS;
	$content = '


	';
	return $content;
}
?>