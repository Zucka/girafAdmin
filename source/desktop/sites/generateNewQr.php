<?php
session_start();
header('Content-type: application/json');
//Check if user is logged in
if ($_SESSION['session_id'] != session_id())
{
	echo '
		{
			"status": "error"
		}
	';
	exit(0);
} 
$userId = $_SESSION['userId'];
require_once ('../db/new.db.php');

$newQr = generateNewQr();

db_insertNewQrCode($session,$userId,$newQr);
echo '
	{
		"status": "ok"
	}
';
/* 
	Generate new QR code

	returns a 512 character string 
*/
function generateNewQr()
{
	$qr = "";
	for ($i=0; $i < 4; $i++) { 
		$time = microtime();
		$qr .= hash("sha512",$time);
		usleep(100); // sleep for 100 microseconds (0.1 milliseconds) to get a different time from microtime
	}
	return $qr;
}

?>