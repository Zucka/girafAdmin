$(window).ready(function() {
	$("#soundFile").on('change',function(){
		var reader = new FileReader(); 
		
		reader.readAsDataURL(this.files[0]);
		
		reader.onload = function (e) {
			$('#audioContainer').html('<audio id="audioElement" controls>'+
										'<source id="soundElement" src="'+e.target.result+'">'+
										picsManagerMake_js_soundElementAlt+
									'<audio>');					
		}
	});
		
	$('#uploadImage').on('change', function(){
		readURL(this);
	});
	
	//Make sure that submit can not be pressed unless there is a title. (Also check in PHP)
	$("#titel").on('change',function(){
		checkIfTitleIsSet();
	});
	$("#titel").on('keyup',function(){
		checkIfTitleIsSet();
	});
	function checkIfTitleIsSet(){
		if($("#titel").val() != ""){
			$("#submit").prop({disabled: false});
		}
		else{
			$("#submit").prop({disabled: true});
		}
	}
	
	if(uploadError.length == 1){
		//Write the Error message from the profilePicUpload
		
		var errorString = "";
		var actualError = "";
		
		switch(uploadError){
			case "0":
				actualError = picsManagerMake_js_actualError0part1+' <a href="#category">'+picsManagerMake_js_actualError0part2+'</a>';
				modalTitel = picsManagerMake_js_succesTitel;
			break;
			
			case "1":
				actualError = picsManagerMake_js_actualError1;
				modalTitel = picsManagerMake_js_errorTitel;
			break;
			
			case "2":
				actualError = picsManagerMake_js_actualError2;
				modalTitel = picsManagerMake_js_errorTitel;
			break;
			
			case "3":
				actualError = picsManagerMake_js_actualError3;
				modalTitel = picsManagerMake_js_errorTitel;
			break;
			
			case "4":
				actualError = picsManagerMake_js_actualError4;
				modalTitel = picsManagerMake_js_errorTitel;
			break;
			
			case "6":
				actualError = picsManagerMake_js_actualError6;
				modalTitel = picsManagerMake_js_errorTitel;
			break;
			
			default:
				actualError = picsManagerMake_js_actualError5;
				modalTitel = picsManagerMake_js_errorTitel;
		}
		
		errorString = '<span class="red">' + actualError + '...</span>'+"\n\n";
		
		changeModalInner(modalTitel,errorString);
		openModal();
	}
});

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {
			$('#tempDisplay').attr('src', e.target.result);
		}

		reader.readAsDataURL(input.files[0]);
		
		$('img#tempDisplay').imgAreaSelect({
			disable: true,
			hide: true
		});		
	}
}