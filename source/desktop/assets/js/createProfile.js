$(document).ready(function() {
	// Handler for .ready() called.
	$("#profile").change( function(){
		// check input ($(this).val()) for validity here
		if($(this).val() == "2" || $(this).val() == "3"){
			$("#emailContainer").html(languageEmail + ':<br><input class="profileInput" name="email" onkeyup="dataCheck()" onchange="dataCheck()" type="text" id="inputEmail" required placeholder="">*<br>');
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
	
	if(uploadError.length == 1){
		//Write the Error message from the profilePicUpload
		
		var errorString = "";
		var actualError = "";
		
		switch(uploadError){
			case "0":
				actualError = createProfile_js_actualError0part1 + ' <a href="#addRelation">' + createProfile_js_actualError0part2 + '</a>';
				modalTitel = createProfile_js_succesTitel;
			break;
			
			case "1":
				actualError = createProfile_js_actualError1;
				modalTitel = createProfile_js_errorTitel;
			break;
			
			case "2":
				actualError = createProfile_js_actualError2;
				modalTitel = createProfile_js_errorTitel;
			break;
			
			default:
				actualError = createProfile_js_actualError3;
				modalTitel = createProfile_js_errorTitel;
		}
	
		reportString = '<span class="red">' + actualError + '...</span><br><br>';
		
		changeModalInner(modalTitel,reportString);
		openModal();
	}
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
  
