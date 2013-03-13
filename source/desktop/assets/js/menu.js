$(function() {
    $(window).bind('resize', function()
    {
        resizeMe();
        }).trigger('resize');
    });
	
function resizeMe(){
	//Standard height, for which the body font size is correct
	var preferredHeight = 768;  

	var displayHeight = $(window).width();
	var percentage = displayHeight / preferredHeight;
	var newFontSize = Math.floor(10 * percentage) - 1;
	if(newFontSize < 10){
		newFontSize = 10;
	}
	$("body").css("font-size", newFontSize);
}