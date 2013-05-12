<?php
	session_start();
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/create_profile/create_profile.en.php');
			break;
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/create_profile/create_profile.dk.php');
			break;
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/create_profile/create_profile.en.php');
			break;
	}
	
?>
	
<script> 
	var languageEmail = "<?php echo $CREATEPROFILE_STRINGS["email"];?>";
</script>
	

<meta charset="utf-8">
<div class="breadcrump">
	<a href="/#profiles"><?php echo $CREATEPROFILE_STRINGS['profiles']; ?></a> → <?php echo $CREATEPROFILE_STRINGS['createProfile']; ?>
</div>

<!--JavaScript-->
<script src="assets/js/create_profile.js"> </script>

				<form class="form-signin" action="/#createProfile">
					<div class="profileType"> 
					Profil:
					<select name="profile" class="span5 profileInput"  id="profile" vertical-align="middle">
						<option value="1"><?php echo $CREATEPROFILE_STRINGS["child"];?></option>
						<option value="2"> <?php echo $CREATEPROFILE_STRINGS["parant"];?></option>
						<option value="3"> <?php echo $CREATEPROFILE_STRINGS["guardian"];?></option>
					</select></div><br>
					<?php echo $CREATEPROFILE_STRINGS["name"];?>:<br>
					<input class="profileInput" id="name" type="text" placeholder="" required>*<br>
					<?php echo $CREATEPROFILE_STRINGS["address"];?>:<br>
					<input class="profileInput" type="text" placeholder=""><br>
					<?php echo $CREATEPROFILE_STRINGS["city"];?>: <br>
					<input class="profileInput" type="text" id="inputWarning" onkeyup="zipCode(this.value)"><br>
					<?php echo $CREATEPROFILE_STRINGS["zipcode"];?>:<br>
					<input class="profileInput" type="text" class="span4" type="text" placeholder=""><br>
					<?php echo $CREATEPROFILE_STRINGS["phone"];?>:<br>
					<input class="profileInput" type="text" placeholder=""><br>
					<?php echo $CREATEPROFILE_STRINGS["mobile"];?>:<br>
					<input class="profileInput" type="text"  placeholder=""><br>
					<div id="emailContainer"></div>
					<div color="#FF0000"><?php echo $CREATEPROFILE_STRINGS["dataRequired"];?></div>
					
				</form>
				<br>
				<div align="center">
					<button class="btn btn-large" id="create" type="button" disabled><i class="icon-user"></i> <?php echo $CREATEPROFILE_STRINGS["createprofile"];?></button>
				</div>


