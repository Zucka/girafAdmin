<?php
	session_start();
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/relations/relations.en.php');
			break;
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/relations/relations.dk.php');
			break;
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/relations/relations.en.php');
			break;
	}
?>

<meta charset="utf-8">
<div class="breadcrump">
	<a href="/#profiles"><?php echo $RELATIONS_STRINGS['profiles']; ?></a> → <?php echo $RELATIONS_STRINGS['relations']; ?>
</div>
				
<div class="container-fluid row">
	<div class="span4 offset1">
		<h3> <?php echo $RELATIONS_STRINGS['children']; ?></h3>
		<table class="table table-striped">
			<tr>
				<th><?php echo $RELATIONS_STRINGS['name'];?></th>
				<th class="span2"><?php echo $RELATIONS_STRINGS['addRelation'];?></th>
			</tr>
			<tr>
				<td>Jens Hansen</td>
				<td  style= "text-align: center;">
					<input type="checkbox" id="child1" value="">
				</td>
			</tr>
			<tr>
				<td>Hans Jensen</td>
				<td  style= "text-align: center;">
					<input type="checkbox" id="child2" value="">
				</td>
			</tr>
			<tr>
				<td>Pia Larsen</td>
				<td  style= "text-align: center;">
					<input type="checkbox" id="child3" value="">
				</td>
			</tr>
			<tr>
				<td>Jens Hansen</td>
				<td style= "text-align: center;">
					<input type="checkbox" id="child4" value="">
				</td>
			</tr>
		</table>
	</div>
	<div class="span4 offset2">
		<h3><?php echo $RELATIONS_STRINGS['parantAndPedagogue'];?></h3>
		<table class="table table-striped">
			<tr>
				<th><?php echo $RELATIONS_STRINGS['name'];?></th>
				<th class="span2"><?php echo $RELATIONS_STRINGS['addRelation']; ?></th>
			</tr>
			<tr>
				<td>Jens Hansen</td>
				<td  style= "text-align: center;">
					<input type="checkbox" id="guardian1" value="">
				</td>
			</tr>
			<tr>
				<td>Hans Jensen</td>
				<td style= "text-align: center;">
					<input type="checkbox" id="guardian2" value="">
				</td>
			</tr>
			<tr>
				<td>Pia Larsen</td>
				<td style= "text-align: center;">
					<input type="checkbox" id="guardian3" value="">
				</td>
			</tr>
			<tr>
				<td>Jens Hansen</td>
				<td style= "text-align: center;">
					<input type="checkbox" id="guardian4" value="">
				</td>
			</tr>
		</table>
	</div>
</div>
			
<div align="center">
	<button class="btn btn-large" type="button"><i class="icon-wrench" ></i> <?php echo $RELATIONS_STRINGS['createRelation'];?></button>
</div>