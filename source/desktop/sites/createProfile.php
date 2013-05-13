<?php
	session_start();
	
	if(isset($_GET['e'])){
		echo '<script> var uploadError = "'.$_GET['e'].'";</script>';
	}
	else{
		echo '<script> var uploadError = "" </script>';
	}
	
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/createProfile/createProfile.en.php');
			echo '<script src="assets/lang/createProfile_js/createProfile_js.en.js"></script>';
			break;
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/createProfile/createProfile.dk.php');
			echo '<script src="assets/lang/createProfile_js/createProfile_js.dk.js"></script>';
			break;
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/createProfile/createProfile.en.php');
			echo '<script src="assets/lang/createProfile_js/createProfile_js.en.js"></script>';
			break;
	}
	
?>
	
<script> 
	var languageEmail = "<?php echo $CREATEPROFILE_STRINGS["email"];?>";
</script>
	

<meta charset="utf-8">
<div class="breadcrump">
	<br>
	<h4><a href="/#profiles"><?php echo $CREATEPROFILE_STRINGS['profiles']; ?></a> → <?php echo $CREATEPROFILE_STRINGS['createProfile']; ?></h4>
</div>

<!--JavaScript-->
<script src="assets/js/createProfile.js"> </script>

<form class="form-signin" action="/#createProfile" method="POST">
	<div class="profileType"> 
	<?php echo $CREATEPROFILE_STRINGS["profile"];?>:
	<select name="profile" class="span5 profileInput"  id="profile" vertical-align="middle">
		<option value="1"><?php echo $CREATEPROFILE_STRINGS["child"];?></option>
		<option value="2"> <?php echo $CREATEPROFILE_STRINGS["parant"];?></option>
		<option value="3"> <?php echo $CREATEPROFILE_STRINGS["guardian"];?></option>
	</select></div><br>
	<?php echo $CREATEPROFILE_STRINGS["name"];?>:<br>
	<input class="profileInput" name="name" id="name" type="text" placeholder="" required>*<br>
	<?php echo $CREATEPROFILE_STRINGS["address"];?>:<br>
	<input class="profileInput" name="address" type="text" placeholder=""><br>
	<?php echo $CREATEPROFILE_STRINGS["city"];?>: <br>
	<input class="profileInput" name="city" type="text" id="inputWarning" onkeyup="zipCode(this.value)"><br>
	<?php echo $CREATEPROFILE_STRINGS["zipcode"];?>:<br>
	<input class="profileInput" name="zipcode" type="text" class="span4" type="text" placeholder=""><br>
	<?php echo $CREATEPROFILE_STRINGS["phone"];?>:<br>
	<input class="profileInput" name="phone" type="text" placeholder=""><br>
	<?php echo $CREATEPROFILE_STRINGS["mobile"];?>:<br>
	<input class="profileInput" name="mobile" type="text"  placeholder=""><br>
	<div id="emailContainer"></div>
	<div color="#FF0000"><?php echo $CREATEPROFILE_STRINGS["dataRequired"];?></div>
	<br>
	<div align="center">
		<input type="submit" class="btn btn-large" id="create" name="createProfileSubmit" type="button" disabled value=" <?php echo $CREATEPROFILE_STRINGS["createProfile"];?>">
	</div>
</form>



