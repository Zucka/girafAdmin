<?php 
	$_POST['profile'];
	$_POST['name'];
	$_POST['address'];
	$_POST['city'];
	$_POST['zipcode'];
	$_POST['phone'];
	$_POST['mobile'];
	$_POST['email'];
	
	function error($error){
		header('Location: /#createProfile/e='.$error);
		exit;
	}
	
	isset($_POST['name']) or error("1");
	
	if($_POST['profile'] != "1"){
		isset($_POST['email']) or error("2");
	}
	
	//make db call
	
	header('Location: /#createProfile/e=0');
?>