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

	<div class="modalFadeWindow"></div>
	<div class="modalContainer">
		<div class="realModal">
			<div class="modalHeader">
				<button type="button" class="close modalClose">&times;</button>
				<h3>Modal Window</h3>
			</div>
			<div class="modalBody">
				
			</div>
			<div class="modalFooter">
				<button type="button" class="btn modalClose"><?php echo $NAVIGATION_STRINGS['modalClose'];?></button>
			</div>
		</div>
	</div>
	
	
	<div class="row-fluid">
		<div class="span2" id="menuBarOuter">
			<div id="menuBar">
				<a href="#ownProfile" class="menuLink menuLastLink"><?php echo $NAVIGATION_STRINGS['ownProfile'];?></a>
					<hr class="menuLinkSplitter">
				<a href="#profiles" class="menuLink deptOnly"><?php echo $NAVIGATION_STRINGS['profiles'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#addRelation" class="menuLink menuSubLink deptOnly menuLastLink"><?php echo $NAVIGATION_STRINGS['addRelation'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#createProfile" class="menuLink menuSubLink deptOnly menuLastLink"><?php echo $NAVIGATION_STRINGS['createProfile'];?></a>
					<hr class="menuLinkSplitter">
				<a href="#picsManager" class="menuLink"><?php echo $NAVIGATION_STRINGS['picsManager']; ?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#makePic" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['makePic'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#addPic" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['addPic'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#removePic" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['removePic'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#editPic" class="menuLink menuSubLink"><?php echo $NAVIGATION_STRINGS['editPic'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#deletePic" class="menuLink menuSubLink menuLastLink"><?php echo $NAVIGATION_STRINGS['deletePic'];?></a>
					<hr class="menuLinkSplitter">
				<a href="#depManager/action=department" class="menuLink deptOnly"><?php echo $NAVIGATION_STRINGS['depManager'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#depInfo/action=department" class="menuLink menuSubLink deptOnly"><?php echo $NAVIGATION_STRINGS['depInfo'];?></a>
					<hr class="menuSubLinkSplitter">
				<a href="#qrManager" class="menuLink menuSubLink deptOnly menuLastLink"><?php echo $NAVIGATION_STRINGS['qrManager'];?></a>
					<hr class="menuLinkSplitter">
				<a href="#appManager" class="menuLink menuLastLink"><?php echo $NAVIGATION_STRINGS['appManager'];?></a>
					<hr class="menuLinkSplitter">

				<a href="logout.php"><button id="menuLogOutButton" class="btn"><?php echo $NAVIGATION_STRINGS['logOut'];?></button></a>

				<img id="menuLogo" src="assets/img/girafAdminLogo-01.svg">
			</div>
		</div>
		<div class="span10" id="content">
			<div >
				
			</div>
		</div>
	</div>
</body>
</html>
