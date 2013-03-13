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
			<a href="#" class="menuLink menuLastLink"><?php echo $NAVIGATION_STRINGS['ownProfile'];?></a>
				<hr class="menuLinkSplitter">
			<a href="#" class="menuLink deptOnly"><?php echo $NAVIGATION_STRINGS['profiles'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink deptOnly menuLastLink"><?php echo $NAVIGATION_STRINGS['addRelation'];?></a>
				<hr class="menuLinkSplitter">
			<a href="#" class="menuLink"><?php echo $NAVIGATION_STRINGS['picsManager']; ?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['makePic'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['addPic'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['removePic'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['editPic'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink menuLastLink"><?php echo $NAVIGATION_STRINGS['deletePic'];?></a>
				<hr class="menuLinkSplitter">
			<a href="#" class="menuLink deptOnly"><?php echo $NAVIGATION_STRINGS['depManager'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink deptOnly"><?php echo $NAVIGATION_STRINGS['depInfo'];?></a>
				<hr class="menuSubLinkSplitter">
			<a href="#" class="menuLink menuSubLink deptOnly menuLastLink"><?php echo $NAVIGATION_STRINGS['qrManager'];?></a>
				<hr class="menuLinkSplitter">
			<a href="#" class="menuLink menuLastLink"><?php echo $NAVIGATION_STRINGS['appManager'];?></a>
				<hr class="menuLinkSplitter">
			
			<a href="logout.php"><button id="menuLogOutButton" class="btn"><?php echo $NAVIGATION_STRINGS['logOut'];?></button></a>
			
			<img id="menuLogo" src="assets/img/girafAdminLogo-01.svg">
		</div>
	</body>
</html>
