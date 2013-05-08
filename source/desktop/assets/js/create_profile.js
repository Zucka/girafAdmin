$(document).ready(function() {
	// Handler for .ready() called.
	$("#profile").change( function(){
		// check input ($(this).val()) for validity here
		if($(this).val() == "2" || $(this).val() == "3"){
			$("#emailContainer").html(languageEmail + ':<br><input class="profileInput" onkeyup="dataCheck()" onchange="dataCheck()" type="text" id="inputEmail" required placeholder="">*<br>');
		} 
		else {
			$("#emailContainer").html("");
		}
	});
		
	$(".profileInput").keyup( function(){
		dataCheck();		
	
	});
	$(".profileInput").change( function(){
		dataCheck();		
	
	});
});

 function dataCheck(){
	//check if #profile == 2 || 3
	//if true #email is set
	//if false only check name
	if($('#name').val() != ""){
	 	if($('#profile').val() == "2" || $('#profile').val() == "3"){
			var email = $('#inputEmail');
			var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			if (filter.test(email.val())){
				$('#create').removeAttr('disabled');
			}
			else{
				$('#create').attr('disabled','disabled');
			}
		}
		else{
			$('#create').removeAttr('disabled');
		}
	}
	else{
		$('#create').attr('disabled','disabled');
	}
} 
  
