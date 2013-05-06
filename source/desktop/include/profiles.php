<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- Force document mode to IE9 standards -->
<?php


if(isset($_GET['e'])){
	echo '<script> var profilePicUploadError = "'.$_GET['e'].'";</script>';
}
else{
	echo '<script> var profilePicUploadError = "" </script>';
}

?>