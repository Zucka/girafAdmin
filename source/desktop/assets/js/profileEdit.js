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

function closeModal(){
	$('.modalFadeWindow').fadeOut(500,"linear");
	$('.modalContainer').fadeOut(500,"linear");
	$('.realModal').fadeOut(500,"linear");
}

function changeProfilePicturePopup(){
	//Make background
	$('body').append('<div class="modalFadeWindow"></div>');
	
	//Make modal window
	//Request picture
	$('body').append('<div class="modalContainer">'+
						'<div class="realModal">'+
							'<div class="modalHeader">'+
								'<button type="button" class="close" onclick="closeModal()">&times;</button>'+
								'<h3>Change Profile Picture</h3>'+
							'</div>'+
							'<div class="modalBody">'+
								'<form id="profilePictureUpload" method="post" enctype="multipart/form-data" action="profilePicTempUpload.php">'+
									'Select image-file: <input type="file" name="newProfilePic" id="newProfilePic" />'+
								'</form>'+
							'</div>'+
							'<div class="modalFooter">'+
								'<button type="button" class="btn" onclick="closeModal()">Close</a>'+
							'</div>'+
							'<div id="profilePic"></div>'+
						'</div>'+
					'</div>');
	
	$('.modalFadeWindow').animate({
		opacity: 0.4
	}, { duration: 500, queue: false });
	$('.modalContainer').animate({
		opacity:1
	}, { duration: 500, queue: false });
	$('.realModal').animate({
		opacity:1
	}, { duration: 500, queue: false });
	//On upload file
	//Call function to change to next modal window
	$('#newProfilePic').on('change', function(){
		changeProfilePicture();
	});
	
}

function changeProfilePicture(){
	//Change Modal window to contain the Requested picture
	$("#preview").html('');
	$("#preview").html('<img src="loader.gif" alt="Uploading...."/>');
	$("#imageform").ajaxForm(
	{
		target: '#preview'
	}).submit();
	//Display picture only in certain height and width, but remmember real height and width
	//Wait for rezise cutting
	
	//Calculate the real cutting values
	
	//Cut and store profile picture
	
	//Change profile picture on site to new profilepicture
	
	//Close modal window and background
}