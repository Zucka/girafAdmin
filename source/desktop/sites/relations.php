<?php
	session_start();
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include('../assets/lang/relations/relations.en.php');
			break;
		case 'dk':
			include('assets/lang/relations/relations.dk.php');
			break;
		default:
			include('assets/lang/relations/relations.en.php');
			break;
	}
?>

<?php
echo '
	<meta charset="utf-8">
	<div class="breadcrump">
	<a href="/#profiles">'.$RELATIONS_STRINGS["profiles"].'</a> → <a href="/#relations">'.$RELATIONS_STRINGS["relations"].'</a>
	</div>
				
	<div class="container-fluid row">
		<div class="span4 offset1">
			<h3> Børn</h3>
			<table class="table table-striped">
				<tr>
					<th>Navn</th>
					<th class="span1">Tilføj Relation</th>
				</tr>
				<tr>
					<td>Jens Hansen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
				<tr>
					<td>Hans Jensen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
				<tr>
					<td>Pia Larsen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
				<tr>
					<td>Jens Hansen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
			</table>
		</div>
		<div class="span4 offset2">
			<h3> Forældrer og Pædagoger</h3>
			<table class="table table-striped">
				<tr>
					<th>Navn</th>
					<th class="span1">Tilføj Relation</th>
				</tr>
				<tr>
					<td>Jens Hansen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
				<tr>
					<td>Hans Jensen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
				<tr>
					<td>Pia Larsen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
				<tr>
					<td>Jens Hansen</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="inlineCheckbox1" value="option1">
						</label>
					</td>
				</tr>
			</table>
		</div>
	</div>
				
	<div align="center">
		<button class="btn btn-large" type="button"><i class="icon-wrench" ></i> Opret Relation</button>
	</div>
?>