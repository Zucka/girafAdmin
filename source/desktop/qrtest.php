<?php
require_once('db/db.php');
require_once('include/phpqrcode/qrlib.php');
session_start();
if ($_SESSION['session_id'] != session_id())
{
	header('location:login.php');
}
$username = $connection->real_escape_string($_SESSION['username']);
$result = $connection->query("SELECT certificate FROM AuthUsers WHERE username='".$username."' limit 1");
$row = $result->fetch_assoc();
QRcode::svg($row['certificate']);
?>