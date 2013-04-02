$(window).load(function() {
	$(".buttonEdit").click(function () {
		editProfileInfo($(this));
	});
	
	$(".profilePictureButton").click(function(){
		changeProfilePicturePopup();
	});
});

function editProfileInfo(element){
	//Edit Button
	//TODO: Remmember to add Language Specific
	element.html('<i class=\"icon-hdd\"></i> Gem');
	element.unbind(); //Clear Event Handler
	element.click(function () {  //Set new Event Handler
		submitEditProfileInfo(element);
	});
	
	//Edit Text Area
	var originalTextElement = element.parent().prev();
	var originalText = originalTextElement.html();
	originalTextElement.html('<input type="text" value=\"'+originalText+'\" />');
}

function submitEditProfileInfo(element){
	//Edit Button
	element.unbind(); //Clear Event Handler
	element.click(function () { //Set new Event Handler
		editProfileInfo(element);
	});
	//TODO: Remmember to add Language Specific
	element.html("<i class=\"icon-wrench\"></i> Ret");
	var textElement = element.parent().prev();
	var newText = textElement.children().val();
	
	//Do AJAX call to submit data 
	
	//Edit textField back to normal
	textElement.html(newText);
}

function changeProfilePicturePopup(){
	//Request picture
	changeModalInner('Change Profile Picture',
					'<form id="profilePictureUpload" method="post" enctype="multipart/form-data" action="profilePicTempUpload.php">'+
						'Select image-file: <input type="file" name="newProfilePic" id="newProfilePic" />'+
						'<input name="x1" id="x1" type="hidden" value="NULL">'+
						'<input name="y1" id="y1" type="hidden" value="NULL">'+
						'<input name="x2" id="x2" type="hidden" value="NULL">'+
						'<input name="y2" id="y2" type="hidden" value="NULL">'+
					'</form>'+
					'<img src="#" alt="Editorial Profile Picture" id="profileCropImage">');
	openModal();
	
	//On upload file
	//Call function to change to next modal window
	$('#newProfilePic').on('change', function(){
		readURL(this);
		
	});
	
}

function changeProfilePicture(){
	//Change Modal window to contain the Requested picture

	
	//Display picture only in certain height and width, but remmember real height and width
	//Wait for rezise cutting
	
	//Calculate the real cutting values
	
	//Cut and store profile picture
	
	//Change profile picture on site to new profilepicture
	
	//Close modal window and background
}

function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#profileCropImage').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
			
			$('img#profileCropImage').imgAreaSelect({
				handles: true,
				aspectRatio: '4:3',
				onSelectEnd: function (img, selection) {
					/*document.getElementById("x1").value = selection.x1;
					document.getElementById("y1").value = selection.y1;
					document.getElementById("x2").value = selection.x2;
					document.getElementById("y2").value = selection.y2;*/
					alert('test');
				}
			});
			
			/*var editPic = $('img#profileCropImage').imgAreaSelect({ instance: true });
			editPic.setSelection(0,0,100,100, true);
			editPic.update();*/
        }
    }