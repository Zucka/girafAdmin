<?php
	if(!empty($_POST['x1'])){
		include_once "include/SimpleImage.php";
		
		$finalWidth = 400;
		$finalHeight = 400;
		$x1 = $_POST['x1'];
		$x2 = $_POST['x2'];
		$y1 = $_POST['y1'];
		$y2 = $_POST['y2'];
		$currentWidth = $_POST['currentWidth'];
		
		//make sure $_POST['profileURL'] is without $_GET-errors
		if(0 <= strpos("e=",$_POST['profileURL'])){
			$urlString = explode("/",$_POST['profileURL']);
			$urlString[2] = preg_replace("|e=.|",'',$urlString[2]);
			$_POST['profileURL'] = $urlString[0]."/".$urlString[1]."/".$urlString[2];
		}
		
		
		// make an error handler which will be used if the upload fails
		function error($error){			
			header('Location: '.$_POST['profileURL'].'/e='.$error);
			exit;
		}

		// check the upload form was actually submitted else print form
		isset($_POST['submit'])
			or error('1');
		
		// check at least one file was uploaded
		isset($_FILES['newProfilePic']['size'])
			or error('2');
				
		// check that the file we are working on really was an HTTP upload
			@is_uploaded_file($_FILES['newProfilePic']['tmp_name'])
				or error('3');
				
		// validation... since this is an image upload script we
		// should run a check to make sure the upload is an image
			@getimagesize($_FILES['newProfilePic']['tmp_name'])
				or error('4');
			
		//Crop picture
		$image = new SimpleImage();
		$image->load($_FILES['newProfilePic']['tmp_name']);
		
		//Find ratio between original and Script Resize
		$ratio = ($image->getWidth())/$currentWidth;
		
		$image->resizeCordsColor($finalWidth,$finalHeight,intval($x1*$ratio),intval($y1*$ratio),intval($x2*$ratio),intval($y2*$ratio),255,255,255);
		$image->save("tempTest/profilePic.jpeg");
		
		header('Location: '.$_POST['profileURL']);
	}
	else{
		echo "Upload Error: Permission Denied.";
	}
?>
