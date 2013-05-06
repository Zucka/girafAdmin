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