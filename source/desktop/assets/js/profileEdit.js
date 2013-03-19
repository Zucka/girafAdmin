function editProfileInfo(element){
	element.html('<i class=\"icon-hdd\"></i> Gem');
	element.unbind();
	element.click(function () { 
			submitEditProfileInfo(element);
		});
	
	var originalTextElement = element.parent().prev();
	var originalText = originalTextElement.html();
	alert(originalText);
	originalTextElement.html('<input type="text" value=\"'+originalText+'\" />');
}
$(window).load(function() {
	$(".buttonEdit").click(function () {
		editProfileInfo($(this));
	});
});

function submitEditProfileInfo(element){
	//Edit Button
	element.unbind();
	element.click(function () { 
			editProfileInfo(element);
		});
	//TODO: Remmember to add Language Specific
	element.html("<i class=\"icon-wrench\"></i> Ret");
	var textElement = element.parent().prev();
	var newText = textElement.children().val();
	
	alert(newText);
	
	
	//Do AJAX call to submit data 
	
	//Edit textField back to normal
	textElement.html(newText);
}