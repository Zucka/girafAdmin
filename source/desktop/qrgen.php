<?php
/*
require_once('db/db.php');
require_once('include/phpqrcode/qrlib.php');
session_start();
if ($_SESSION['session_id'] != session_id())
{
	exit();
}
$username = $connection->real_escape_string($_SESSION['username']);
$result = $connection->query("SELECT certificate FROM AuthUsers WHERE username='".$username."' limit 1");
$row = $result->fetch_assoc();
*/
require_once('include/phpqrcode/qrlib.php');
if (!isset($_GET['qr']))
{
	exit();
}

echo QRcode::svg($_GET['qr'],false,4,4,false,0xFFFFFF,0x000000);
?>