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
		
		// make an error handler which will be used if the upload fails
		function error($error){
		
			echo 
			'    <div id="Upload">'."\n\n".
			'        <h1>Upload failure</h1>'."\n\n".
			'        <p>An error has occured: '."\n\n".
			'        <span class="red">' . $error . '...</span>'."\n\n".
			'     </div>'."\n\n";
			
			exit;
		} // end error handler
		
		//Set Variables
		// make a note of the current working directory, relative to root.
		$directory_self = str_replace(basename($_SERVER['PHP_SELF']), '', $_SERVER['PHP_SELF']);

		// Now let's deal with the uploaded files

		// possible PHP upload errors
		$errors = array(1 => 'php.ini max file size exceeded',
						2 => 'html form max file size exceeded',
						3 => 'file upload was only partial',
						4 => 'no file was attached');
		
		// check the upload form was actually submitted else print form
		isset($_POST['submit'])
			or error('the upload form is neaded');
		
		// check at least one file was uploaded
		isset($_FILES['newProfilePic']['size'])
			or error('No files were uploaded');
				
		// check that the file we are working on really was an HTTP upload
			@is_uploaded_file($_FILES['newProfilePic']['tmp_name'])
				or error($_FILES['newProfilePic']['tmp_name'].' not an HTTP upload');
				
		// validation... since this is an image upload script we
		// should run a check to make sure the upload is an image
			@getimagesize($_FILES['newProfilePic']['tmp_name'])
				or error(' not an image');
			
		//Crop picture
		$image = new SimpleImage();
		$image->load($_FILES['newProfilePic']['tmp_name']);
		
		//Find ratio between original and Script Resize
		$ratio = ($image->getWidth())/$currentWidth;
		
		$image->resizeCordsColor($finalWidth,$finalHeight,intval($x1*$ratio),intval($y1*$ratio),intval($x2*$ratio),intval($y2*$ratio),255,255,255);
		$image->save("tempTest/profilePic.jpeg");

	}
?>
