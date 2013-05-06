<?php
if(isset($_POST['picsManagerMakeSubmit'])){//Make sure the form was used
	echo $_POST['titel'];
	echo $_POST['inlineText'];
	echo $_POST['privacySetting'];
	echo $_POST['tags'];
	echo $_POST['category'];
	
	function error($error){
		header('Location: /#makePic/e='.$error);
				
		exit;
	}
	
	function isAllowedSoundFile($fileName,$fileTmpName){
		$supportedFormats = array('.3gp','.flac','.mp3','.mid','.xmf','.mxmf','.rtttl','.rtx','.ota','.imy','.ogg','.wav');
	}
	
	//Check files
	if(isset($_FILES['uploadImage']['size'])){//Image file was uploaded
		@is_uploaded_file($_FILES['uploadImage']['tmp_name'])// check that the file we are working on really was an HTTP upload
			or error('3');
			
		@getimagesize($_FILES['uploadImage']['tmp_name'])//Check that the file is an image
				or error('4');
				
		//Crop picture
		$image = new SimpleImage();
		$image->load($_FILES['uploadImage']['tmp_name']);
		
		$image->resizeCordsColor(400,400,0,0,$image->getWidth(),$image->getHeight(),255,255,255);
		$image->save("tempTest/picsManagerMake.jpeg");//TODO: Make this line into a DB query
	}
	if(isset($_FILES['soundFile']['size'])){//Sound file was uploaded
		@is_uploaded_file($_FILES['soundFile']['tmp_name'])// check that the file we are working on really was an HTTP upload
			or error('3');
		
		isAllowedSoundFile($_FILES['soundFile']['name'],$_FILES['soundFile']['tmp_name'])
			or error('9000');
		
		
	}
}
else{ //If the user sees this an unforseen error has occured or they tried to refresh the page without submitting data.
	echo "ERROR: An unknown error has occured. Please try again in a few minutes.";
}

?>