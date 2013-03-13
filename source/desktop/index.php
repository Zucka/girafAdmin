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
	<?php include "include/headInclude.php" ?>
	</head>
	<body>
		<div id="menuBar">
			<a href="#" class="menuLink menuLastLink"><strong>E</strong>gen Profil</a>
			<hr class="menuLinkSplitter">
			<a href="#" class="menuLink deptOnly"><strong>P</strong>rofiler</a>
			<a href="#" class="menuLink menuSubLink deptOnly menuLastLink">Tilføj Relation</a>
			<hr class="menuLinkSplitter">
			<a href="#" class="menuLink"><strong>P</strong>ics Manager</a>
			<a href="#" class="menuLink menuSubLink">Opret</a>
			<a href="#" class="menuLink menuSubLink">Tildel</a>
			<a href="#" class="menuLink menuSubLink">Fjern Lokalt</a>
			<a href="#" class="menuLink menuSubLink">Ret</a>
			<a href="#" class="menuLink menuSubLink menuLastLink">Slet Permanent</a>
			<hr class="menuLinkSplitter">
			<a href="#" class="menuLink deptOnly"><strong>D</strong>ep. Manager</a>
			<a href="#" class="menuLink menuSubLink deptOnly">Dep. Information</a>
			<a href="#" class="menuLink menuSubLink deptOnly menuLastLink">QR Manager</a>
			<hr class="menuLinkSplitter">
			<a href="#" class="menuLink menuLastLink"><strong>A</strong>pp Manager</a>
			<hr class="menuLinkSplitter">
			
			<a href="logout.php"><button id="menuLogOutButton" class="btn">Log out</button></a>
			
			<img id="menuLogo" src="assets/img/girafAdminLogo-01.svg">
		</div>
	</body>
</html>
