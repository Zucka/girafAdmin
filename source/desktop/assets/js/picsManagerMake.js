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
	
	if(uploadError.length == 1){
		//Write the Error message from the profilePicUpload
		
		var errorString = "";
		var actualError = "";
		
		switch(uploadError){
			case "0":
				actualError = picsManagerMake_js_actualError0;
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
			
			default:
				actualError = picsManagerMake_js_actualError4;
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