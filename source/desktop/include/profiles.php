<?php


if(isset($_GET['e'])){
	echo '<script> var profilePicUploadError = "'.$_GET['e'].'";</script>';
}
else{
	echo '<script> var profilePicUploadError = "" </script>';
}

?>