<?php
session_start();

//Check if user is logged in
if ($_SESSION['session_id'] != session_id())
{
	header('location:login.php');
} 
?>
<html>
	<head>
	</head>
	<body>
		<a href="logout.php">Log out</a>
	</body>
</html>