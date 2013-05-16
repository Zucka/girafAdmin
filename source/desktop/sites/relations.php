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
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/relations/relations.en.php');
			echo '<script src="assets/lang/relations_js/relationse_js.en.js"></script>';
			break;
		case 'dk':
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/relations/relations.dk.php');
			echo '<script src="assets/lang/relations_js/relationse_js.dk.js"></script>';
			break;
		default:
			include($_SERVER['DOCUMENT_ROOT'].'/assets/lang/relations/relations.en.php');
			echo '<script src="assets/lang/relations_js/relationse_js.en.js"></script>';
			break;
	}
?>

<meta charset="utf-8">
<div class="breadcrump">
	<br>
	<h4><a href="/#profiles"><?php echo $RELATIONS_STRINGS['profiles']; ?></a> → <?php echo $RELATIONS_STRINGS['relations']; ?></h4>
</div>

<!--JavaScript-->
<script src="assets/js/relations.js"> </script>

<?php 
	require_once "../db/new.db.php";
	$result = db_getProfiles();
	if($result!=false){
		$children = array();
		$guardians = array();
		
		foreach ($result as $value){
			if($value['role']=='2'){
				array_push($children, $value);
			}
			//($value['role']=='0'||$value['role']=='1')
			else{
				array_push($guardians, $value);
			}
		}
	}	
?>


<form action="/#relations" method="POST">	
	<div class="container-fluid row">
		<div class="span4 offset1">
			<h3> <?php echo $RELATIONS_STRINGS['children']; ?></h3>
			<table class="table table-striped" id="children">
				<tr>
					<th><?php echo $RELATIONS_STRINGS['name'];?></th>
					<th class="span2"><?php echo $RELATIONS_STRINGS['addRelation'];?></th>
				</tr>
				<?php 
					$i = 0;
					foreach ($children as $value){
						echo '	
							<tr>
								<td>'.$value['name'].'</td>
								<td  style= "text-align: center;">
								<input type="checkbox" name="child'.$i.'" class="checkbox child" value="'.$value['id'].'">
								</td>
							</tr>
						';
						$i++;
					}
				?>
				<input type="hidden" name="numOfChildren" value="<?php echo count($children);?>">
			</table>
		</div>
		<div class="span4 offset2">
			<h3><?php echo $RELATIONS_STRINGS['parantAndPedagogue'];?></h3>
			<table class="table table-striped" id="guardian">
				<tr>
					<th><?php echo $RELATIONS_STRINGS['name'];?></th>
					<th class="span2"><?php echo $RELATIONS_STRINGS['addRelation']; ?></th>
				</tr>
				
				<?php 
					$i=0;
					foreach ($guardians as $value){
						echo '	
							<tr>
								<td>'.$value['name'].'</td>
								<td  style= "text-align: center;">
								<input type="checkbox" name="guardian'.$i.'" class="checkbox guardian" value="'.$value['id'].'">
								</td>
							</tr>
						';
						$i++;
					}
					
				?>
				<input type="hidden" name="numOfGuardians" value="<?php echo count($guardians);?>">
			</table>
		</div>	
	</div>
			
	<div align="center">
		<input type="submit" class="btn btn-large" id="create" name="createProfileSubmit" type="button" disabled value=" <?php echo $RELATIONS_STRINGS['createRelation'];?>">
	</div>
</form>