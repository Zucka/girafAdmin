function editProfileInfo(buttonElement){
	alert(buttonElement);
	//Edit Button and textField
	buttonElement.onclick = submitEditProfileInfo(buttonElement);
	//TODO: Remmember to add Language Specific
	buttonElement.html = "<i class=\"icon-hdd\"></i> Gem";
	var originalTextElement = buttonElement.parentNode.previousSibling;
	var originalText = originalTextElement.html;
	originalTextElement.html = '<input type="text">'+originalText+'</input>';
}

function submitEditProfileInfo(){
	//Edit Button
	//buttonElement.onclick = editProfileInfo(buttonElement);
	//TODO: Remmember to add Language Specific
	buttonElement.html = "<i class=\"icon-wrench\"></i> Ret";
	var textElement = buttonElement.parentNode.previousSibling;
	var newText = textElement.child.html;
	
	//Do AJAX call to submit data 
	
	//Edit textField back to normal
	textElement.html = newText;
}