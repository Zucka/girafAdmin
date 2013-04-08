<?php
include('db/db.php');
session_start();
$session_id='1'; // User session id
$path = "uploads/";

$valid_formats = array("jpg", "png", "gif", "bmp","jpeg");
if(isset($_POST) and $_SERVER['REQUEST_METHOD'] == "POST")
{
	$name = $_FILES['newProfilePic']['name'];
	$size = $_FILES['newProfilePic']['size'];
	if(strlen($name))
	{
		list($txt, $ext) = explode(".", $name);
		if(in_array($ext,$valid_formats))
		{
			if($size<(1024*1024)) // Image size max 1 MB
			{
				$actual_image_name = time().$session_id.".".$ext;
				$tmp = $_FILES['newProfilePic']['tmp_name'];
				if(move_uploaded_file($tmp, $path.$actual_image_name))
				{
					echo "<img src='uploads/".$actual_image_name."' class='preview'>";
				}
				else
				echo "failed";
			}
			else
			echo "Image file size max 1 MB"; 
		}
		else
		echo "Invalid file format.."; 
	}
	else
	echo "Please select image..!";
	exit;
}
?>