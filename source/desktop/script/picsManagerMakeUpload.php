<?php
if(isset($_POST['picsManagerMakeSubmit'])){//Make sure the form was used
	$_POST['titel'];
	$_POST['inlineText'];
	$_POST['privacySetting'];
	$_POST['tags'];
	$_POST['category'];
	
	function error($error){
		header('Location: /#makePic/e='.$error);
		exit;
	}
	
	function isAllowedSoundFile($fileName,$fileTmpName){
		$supportedExtensions = array('3gp','3gpp','flac','mp3','mid','xmf','mxmf','rtttl','rtx','ota','imy','ogg','wav');
		$supportedMimeTypes = array('audio/mpeg','audio/mp3','audio/mid','audio/wav','audio/x-wav','audio/rtx','audio/3gpp','audio/ogg','audio/mobile-xmf','audio/mxf'); //Could not find mime type of .rtttl, .ota and .imy
		
		//Check file extension
		$ext = pathinfo($fileName, PATHINFO_EXTENSION);
		if(in_array($ext,$supportedExtensions)){
			$fileExtOkay = true;
		}
		else{
			$fileExtOkay = false;
		}
		
		//Check Mime Type
		$finfo = finfo_open(FILEINFO_MIME_TYPE); // return mime type ala mimetype extension
		echo finfo_file($finfo, $fileTmpName); //TODO REMOVE LINE AFTER TESTING
		if(in_array(finfo_file($finfo, $fileTmpName),$supportedMimeTypes)){
			$fileMimeTypeOkay = true;
		}
		else{
			$fileMimeTypeOkay = false;
		}
		finfo_close($finfo);
		
		//return
		if($fileMimeTypeOkay && $fileExtOkay){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Check files
	if(isset($_FILES['uploadImage']['size'])){//Image file was uploaded
		error('3');
		@is_uploaded_file($_FILES['uploadImage']['tmp_name'])// check that the file we are working on really was an HTTP upload
			or error('1');
			
		@getimagesize($_FILES['uploadImage']['tmp_name'])//Check that the file is an image
			or error('2');
				
		//Crop picture
		require_once "include/SimpleImage.php";
		$image = new SimpleImage();
		$image->load($_FILES['uploadImage']['tmp_name']);
		
		$image->resizeCordsColor(400,400,0,0,$image->getWidth(),$image->getHeight(),255,255,255);
		$image->save("tempTest/picsManagerMake.jpeg");//TODO: Make this line into a DB query
	}
	if(isset($_FILES['soundFile']['size'])){//Sound file was uploaded
		@is_uploaded_file($_FILES['soundFile']['tmp_name'])// check that the file we are working on really was an HTTP upload
			or error('1');
		
		isAllowedSoundFile($_FILES['soundFile']['name'],$_FILES['soundFile']['tmp_name'])
			or error('3');
		
		$fh = fopen($_FILES['soundFile']['tmp_name'], 'r');
		$data = fread($fh, filesize($_FILES['soundFile']['tmp_name']));
		fclose($fh);
		$data;
	}
	
	header('Location: /#makePic/e=0');
}
else{ //If the user sees this an unforseen error has occured or they tried to refresh the page without submitting data.
	if (isset($_SESSION['lang'])) {$lang = $_SESSION['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/picsManagerMake/picsManagerMake.dk.php');
			break;
		case 'en':
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/picsManagerMake/picsManagerMake.en.php');
			break;
	}
	
	echo $picsManagerMakeStrings['picsManagerMakeUploadError'];
}

?>