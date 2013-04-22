$(window).ready(function() {
	$(".buttonEdit").click(function () {
		editProfileInfo($(this));
	});
	
	$(".profilePictureButton").click(function(){
		changeProfilePicturePopup();
	});
	//Make sure to close the ImageAreaSelect fields
	$('.modalFadeWindow').click(function(){
		closeModal();
		closeImageAreaSelect();
	});
	$('.modalClose').click(function(){
		closeModal();
		closeImageAreaSelect();
	});
		
	if(profilePicUploadError.length == 1){
		//Write the Error message from the profilePicUpload
		
		var errorString = "";
		var actualError = "";
		
		switch(profilePicUploadError){
			case "1":
				actualError = own_profile_js_actualError1;
			break;
			
			case "2":
				actualError = own_profile_js_actualError2;
			break;
			
			case "3":
				actualError = own_profile_js_actualError3;
			break;
			
			case "4":
				actualError = own_profile_js_actualError4;
			break;
			
			default:
				actualError = own_profile_js_actualError5;
		}
		
		errorString = 
		'        '+own_profile_js_errorStringPart1+': '+"\n\n"+
		'        <span class="red">' + actualError + '...</span>'+"\n\n";
		
		changeModalInner(own_profile_js_errorStringHeader,errorString);
		openModal();
	}
});

function closeImageAreaSelect(){
	$('.imgareaselect-outer').animate({
		opacity: 0
	}, { duration: 500, queue: false });
	$('.imgareaselect-selection').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.imgareaselect-border1').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.imgareaselect-border2').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.imgareaselect-border3').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.imgareaselect-border4').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.imgareaselect-handle').animate({
		opacity:0
	}, 500,function(){
		$('.imgareaselect-outer').remove();
		$('.imgareaselect-selection').parent().remove();
		$('.imgareaselect-selection').remove();
		$('.imgareaselect-border1').remove();
		$('.imgareaselect-border2').remove();
		$('.imgareaselect-border3').remove();
		$('.imgareaselect-border4').remove();
		$('.imgareaselect-handle').remove();
	});
}

function editProfileInfo(element){
	//Edit Button
	//TODO: Remmember to add Language Specific
	element.html('<i class=\"icon-hdd\"></i> '+own_profile_js_editInfoButtonSave);
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
	element.html("<i class=\"icon-wrench\"></i> "+own_profile_js_editInfoButtonEdit);
	var textElement = element.parent().prev();
	var newText = textElement.children().val();
	
	//Do AJAX call to submit data 
	
	//Edit textField back to normal
	textElement.html(newText);
}

function changeProfilePicturePopup(){
	//Request picture
	changeModalInner(own_profile_js_changeProfilePicHeader,
					'<form id="profilePictureUpload" method="post" enctype="multipart/form-data" action="#profilePicUpload">'+
						'Select image-file: <input type="file" name="newProfilePic" id="newProfilePic" />'+
						'<input name="x1" id="x1" type="hidden" value="NULL">'+
						'<input name="y1" id="y1" type="hidden" value="NULL">'+
						'<input name="x2" id="x2" type="hidden" value="NULL">'+
						'<input name="y2" id="y2" type="hidden" value="NULL">'+
						'<input name="profileURL" type="hidden" value="'+document.URL+'">'+ //This means we know where it was called from.
						'<input name="currentWidth" id="currentWidth" type="hidden" value="NULL">'+
						'<center><img src="#" alt="'+own_profile_js_changeProfilePicImgAlt+'" id="profileCropImage"></center>'+
						'<input type="submit" name="submit" value="'+own_profile_js_changeProfilePicSubmit+'" class="btn">'+
					'</form>'
					);
	openModal();
	
	//On upload file
	$('#newProfilePic').on('change', function(){
		readURL(this);
		
		$('img#profileCropImage').imgAreaSelect({
			onInit: function(img, selection){
				var editPic = $('img#profileCropImage').imgAreaSelect({ instance: true });
				editPic.setSelection(100,100,200,200, true);
				editPic.setOptions({ show: true });
				editPic.update();
			}
		});
		
	});
	
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
			aspectRatio: '1:1',
			onSelectEnd: function (img, selection) {
				document.getElementById("x1").value = selection.x1;
				document.getElementById("y1").value = selection.y1;
				document.getElementById("x2").value = selection.x2;
				document.getElementById("y2").value = selection.y2;
				document.getElementById("currentWidth").value = $("#profileCropImage").width();
			}
		});		
	}
}