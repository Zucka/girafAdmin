<?php
	if(!empty($_POST['x1'])){
		require_once "../include/SimpleImage.php";
		
		$finalWidth = 400;
		$finalHeight = 400;
		$x1 = $_POST['x1'];
		$x2 = $_POST['x2'];
		$y1 = $_POST['y1'];
		$y2 = $_POST['y2'];
		$currentWidth = $_POST['currentWidth'];
		$base64Image = $_POST['profileImage'];
		$data = base64_decode($base64Image);

		$image = imagecreatefromstring($data);
		header('Content-Type: image/png');
		imagepng($image,"../tempTest/temp.png");
		
		
		
		
		//header('Content-Type: image/png');
		echo "<br><br>image data: ".$image."<br><br>";
		
		echo "<br><br>Lars tester<br>".getimagesize($image)."<br>Slut<br><br>";
		
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
		/*isset($_FILES['newProfilePic']['size'])
			or error('No files were uploaded');
					
		// check for standard uploading errors
		($_FILES['newProfilePic']['error'] == 0)
				or error($_FILES['newProfilePic']['tmp_name'].': '.$errors[$_FILES['newProfilePic']['error']]);
		*/
				
		// check that the file we are working on really was an HTTP upload
		/*	@is_uploaded_file($_FILES['newProfilePic']['tmp_name'])
				or error($_FILES['newProfilePic']['tmp_name'].' not an HTTP upload');*/
				
		// validation... since this is an image upload script we
		// should run a check to make sure the upload is an image
		/*	@getimagesize($image)
				or error(' not an image');

		// now let's move the file to its final and allocate it with the new filename

		//Upload Full Size
		/*@move_uploaded_file($_FILES['newProfilePic']['tmp_name'], $uploadFilenameBig[$key])
			or error('receiving directory insuffiecient permission,  please contact your web admin for a clean-up');*/
			
		//Crop picture
		$image = new SimpleImage();
		$image->loadFromString($image);
		
		//Find ratio between original and Script Resize
		$ratio = ($image->getWidth())/$currentWidth;
		
		$image->resizeCordsColor($finalWidth,$finalHeight,intval($x1*$ratio),intval($y1*$ratio),intval($x2*$ratio),intval($y2*$ratio),255,255,255);
		$image->save("../tempTest/profilePic.jpeg");
			
		
		
		
		
	}
?>
