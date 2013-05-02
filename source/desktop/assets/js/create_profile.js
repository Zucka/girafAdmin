$(document).ready(function() {
	// Handler for .ready() called.
	$("#profile").change( function(){
		// check input ($(this).val()) for validity here
		if($(this).val() == "2" || $(this).val() == "3"){
			$("#emailContainer").html(languageEmail + ':<br><input class="profileInput" onkeyup="dataCheck()" type="text" id="inputEmail" required placeholder=""><br>');
		} 
		else {
			$("#emailContainer").html("");
		}
	});
		
	$(".profileInput").keyup( function(){
		dataCheck();		
	
	});
});
 function dataCheck(){
	//check if #profile == 2 || 3
	//if true #email is set
	//if false only check name
	if($('#name').val() != ""){
	$('#create').removeAttr('disabled')
	}
	else{
		$('#create').attr('disabled','disabled');
		if($('#name').val() == ""){
			$('#nameForm').toggleClass('control-group warning')
		}
	}
} 
  
