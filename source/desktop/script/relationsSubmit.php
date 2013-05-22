<?php 
	$_POST['profile'];
	$_POST['numOfChildren'];
	$_POST['numOfGuardians'];
	
	$i = 0;
	$children = array();
	while($i <= $_POST['numOfChildren']){
		$childName = "child".$i;
		if(isset($_POST[$childName])){
			array_push($children,$_POST[$childName]);
		}
		$i++;
	}
	$i = 0;
	$guardians = array();
	while($i <= $_POST['numOfGuardians']){
		$guardianName = "guardian".$i;
		if(isset($_POST[$guardianName])){
			array_push($guardians,$_POST[$guardianName]);
		}
		$i++;
	}
	
/* 	foreach($children as $child){
		foreach($guardians as $guardian){
				
			{ 
			"type": data_type 
			"values": 
				[ 
			{ 
			  "id": INT, 
			  "value": value_object 
			}, 
				... 
						] 
					}
				
				
			
			} 
		} 
	}
	else {
	
	
	
	
	} */
	
	function error($error){
		header('Location: /#addRelation/e='.$error);
		exit;
	}
	
	isset($_POST['numOfChildren']) or error("1");
	isset($_POST['numOfGuardians']) or error("2");
	
	//make db call
	
	header('Location: /#addRelation/e=0');
?>
