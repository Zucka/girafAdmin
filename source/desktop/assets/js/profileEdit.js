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
					'</form>');
	openModal();
	
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
	$("#imageform").submit(function(event){
		$.ajax({  
			type: "POST", 
			contentType:attr( "enctype", "multipart/form-data" ),
			url: "script/profilePicTempUpload.php",  
			data: dados,  
			success: function( data )  
			{  
				alert( data );  
			}  
		});  
  
		return false;  
	});
	
	//Display picture only in certain height and width, but remmember real height and width
	//Wait for rezise cutting
	
	//Calculate the real cutting values
	
	//Cut and store profile picture
	
	//Change profile picture on site to new profilepicture
	
	//Close modal window and background
}