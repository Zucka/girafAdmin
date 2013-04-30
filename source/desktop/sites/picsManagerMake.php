
<div id="picsManagerContainer">
	<div class="breadcrump">
	<a href="/#picsManager">Pics Manager</a> → Make
	</div>
	<div class="UploadContainer">
		<form action="/#makePic" method="post" enctype="multipart/form-data">
			<div id="tableBox">
				<table>
					<tr>
						<td>Titel: </td><td><input type="text" name="titel"></td>
					</tr>
					<tr>
						<td>Inline Text: </td><td><input type="text" name="inlineText"></td>
					</tr>
					<tr>
						<td>Privat Indstilling: </td>
						<td>
							<select name="privacySetting"> 
							  <option value="private">Privat</option>
							  <option value="department">Afdeling</option>
							  <option value="public">Offentlig</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Tags: </td><td><input type="text" name="tags"></td>
					</tr>
					<tr>
						<td>Katagori: </td>
						<td>
							<select name="category"> 
							  <option value="heste">Heste</option>
							  <option value="mad">Mad</option>
							  <option value="morgenmad">Morgenmad</option>
							</select>
						</td>
					</tr>
					
				</table>
			</div>
			<div id="imageBox">
				Billede:<br>
				<img id="tempDisplay" src="#" alt="Venter på billede...">
				<input type="file" id="uploadImage" name="uploadImage">
				
				<input type="hidden" id="x1" name="x1">
				<input type="hidden" id="x2" name="x2">
				<input type="hidden" id="y1" name="y1">
				<input type="hidden" id="y2" name="y2">
			</div>
			<div id="soundBox">
				Lyd:<br>
				<input type="file" id="soundFile" name="soundFile">
				<embed height="50" width="100" id="soundPlayer" src="assets/sound/empty.wma">
			</div>
			<div id="submitBox">
				<input type="submit" name="submit" value="Opret">
			</div>
		</form>
	</div>
</div>
