$(window).ready(function() {
	$('#soundFile').on('change', function(){
		if (this.files && this.files[0]) {
			var reader = new FileReader(); 

			reader.onload = function (e) {
				$('#embedContainer').html('<embed height="50" width="100" id="soundPlayer" src="'+e.target.result+'">');
				alert("test1");
			}
			alert("test2");
			
			reader.readAsDataURL(input.files[0]);
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