var goodchild = false;
var goodguardian = false;

$(document).ready(function() {
	// Handler for .ready() called.
	$(".checkbox").on('click', function(){
		dataCheck();	
	});
	
 	if(uploadError.length == 1){
		//Write the Error message from the profilePicUpload
		
		var errorString = "";
		var actualError = "";
		
		switch(uploadError){
			case "0":
				actualError = relations_js_actualError;
				modalTitel = relations_js_succesTitel;
			break;
			
			case "1":
				actualError = relations_js_actualError1;
				modalTitel = relations_js_errorTitel;
			break;
			
			case "2":
				actualError = relations_js_actualError2;
				modalTitel = relations_js_errorTitel;
			break;
			
			default:
				actualError = relations_js_actualError3;
				modalTitel = relations_js_errorTitel;
		}
	
		reportString = '<span class="red">' + actualError + '...</span><br><br>';
		
		changeModalInner(modalTitel,reportString);
		openModal();
	} 
});

 function dataCheck(){
	$(".child").each(function(){
		if(jQuery('#children input[type=checkbox]:checked').length) {goodchild = true;  }else {goodchild = false;} 
	});
	
	$(".guardian").each(function(){
		if(jQuery('#guardian input[type=checkbox]:checked').length) {goodguardian = true;  }else {goodguaridan = false;} 
	});
	
	if(goodchild && goodguardian){
		$('#create').removeAttr('disabled');
	}
	else{
		$('#create').attr('disabled','disabled');
	}	
}	
	
	

  
