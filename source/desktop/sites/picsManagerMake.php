<?php
	session_start();
	if(isset($_GET['e'])){
		echo '<script> var uploadError = "'.$_GET['e'].'";</script>';
	}
	else{
		echo '<script> var uploadError = "" </script>';
	}

	if (isset($_SESSION['lang'])) {$lang = $_SESSION['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/picsManagerMake/picsManagerMake.dk.php');
			echo '<script src="assets/lang/picsManagerMake_js/picsManagerMake_js.dk.js"></script>';
			break;
		case 'en':
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/picsManagerMake/picsManagerMake.en.php');
			echo '<script src="assets/lang/picsManagerMake_js/picsManagerMake_js.en.js"></script>';
			break;
	}
?>
<script src="assets/js/picsManagerMake.js" type="text/javascript"></script>

<div id="picsManagerContainer">
	<div class="breadcrump">
	<a href="/#picsManager">Pics Manager</a> → <?php echo $picsManagerMakeStrings['breadCrumTitle'];?>
	</div>
	<div class="UploadContainer">
		<form action="/#makePicUpload" method="post" enctype="multipart/form-data">
			<div id="tableBox">
				<table>
					<tr>
						<td><?php echo $picsManagerMakeStrings['titel'];?>*: </td><td><input type="text" name="titel" id="titel"></td>
					</tr>
					<tr>
						<td><?php echo $picsManagerMakeStrings['inlineText'];?>: </td><td><input type="text" name="inlineText"></td>
					</tr>
					<tr>
						<td><?php echo $picsManagerMakeStrings['privacySetting'];?>: </td>
						<td>
							<select name="privacySetting"> 
							  <option value="private"><?php echo $picsManagerMakeStrings['private'];?></option>
							  <option value="department"><?php echo $picsManagerMakeStrings['department'];?></option>
							  <option value="public"><?php echo $picsManagerMakeStrings['public'];?></option>
							</select>
						</td>
					</tr>
					<tr>
						<td><?php echo $picsManagerMakeStrings['tags'];?>: </td><td><input type="text" name="tags"></td>
					</tr>
					
				</table>
			</div>
			<div id="imageBox">
				<?php echo $picsManagerMakeStrings['image'];?>:<br>
				<img id="tempDisplay" src="#" alt="<?php echo $picsManagerMakeStrings['imageAlt'];?>">
				<input type="file" id="uploadImage" name="uploadImage">
			</div>
			<div id="soundBox">
				<?php echo $picsManagerMakeStrings['sound'];?>:<br>
				<input type="file" id="soundFile" name="soundFile">
				<div id="audioContainer">
					<audio id="audioElement" controls>
						<source id="soundElement" src="assets/sound/empty.mp3">
						<?php echo $picsManagerMakeStrings['soundAlt'];?>
					<audio>
				</div>
			</div>
			<div id="submitBox">
				<input type="submit" id="submit" name="picsManagerMakeSubmit" value="<?php echo $picsManagerMakeStrings['submit'];?>" disabled="disabled">
			</div>
		</form>
	</div>
</div>
