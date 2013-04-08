<?php
	$x1 = $_POST['x1'];
	$x2 = $_POST['x2'];
	$y1 = $_POST['y1'];
	$y2 = $_POST['y2'];
	$currentWidth = $_POST['currentWidth'];
	$fileSize = $_POST['fileTransfer']['newProfilePic']['size'];
	
	echo "x1: $x1 <br>
		  x2: $x2 <br>
		  y1: $y1 <br>
		  y2: $y2 <br>
		  currentWidth: $currentWidth <br>
		  FileSize: $fileSize
	";
?>
