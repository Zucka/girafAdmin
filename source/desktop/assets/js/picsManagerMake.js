$(window).ready(function() {
	/*$('#soundFile').on('change', function(){
		if (this.files && this.files[0]) {
			

			reader.onload = function (e) {
				$('#embedContainer').html('<embed height="50" width="100" id="soundPlayer" src="'+e.target.result+'">');
				alert("test1");
			}
			alert("test2");
			
			reader.readAsDataURL(this.files[0]);
		}*/
		
	$("#soundFile").on('change',function(){
		var reader = new FileReader(); 
		
		reader.readAsDataURL(this.files[0]);
		
		reader.onload = function (e) {
			$('#audioContainer').html('<audio id="audioElement" controls>'+
										'<source id="soundElement" src="'+e.target.result+'">'+
										'Your browser does not support this audio format.'+
									'<audio>');
			//$('#audioContainer').html('<embed height="50" width="100" id="soundPlayer" src="'+e.target.result+'">');
					
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
				actualError = "Dit pictogram er nu oprettet";
				modalTitel = "Succes";
			break;
			
			case "1":
				actualError = "Du skal bruge formen for at uploade.";
				modalTitel = "Error";
			break;
			
			case "2":
				actualError = "Det billede du uploadede var ikke nogen billed fil.";
				modalTitel = "Error";
			break;
			
			case "3":
				actualError = "Den lyd fil du uploadede er ikke et understøttet format.";
				modalTitel = "Error";
			break;
			
			default:
				actualError = "En uforudset fejl er forekommet.";
				modalTitel = "Error";
		}
		
		errorString = 
		'        '+own_profile_js_errorStringPart1+': '+"\n\n"+
		'        <span class="red">' + actualError + '...</span>'+"\n\n";
		
		changeModalInner(own_profile_js_errorStringHeader,errorString);
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