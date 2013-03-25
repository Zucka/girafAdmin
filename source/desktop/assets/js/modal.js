function closeModal(){
	$('.modalFadeWindow').animate({
		opacity: 0
	}, { duration: 500, queue: false });
	$('.modalContainer').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.realModal').animate({
		opacity:0
	}, { duration: 500, queue: false });
	
	$('.modalFadeWindow').css('z-index','-10');
	$('.modalContainer').css('z-index','-10');
	$('.realModal').css('z-index','-10');
}

function openModal(){
	$('.modalFadeWindow').animate({
		opacity: 0.4
	}, { duration: 500, queue: false });
	$('.modalContainer').animate({
		opacity:1
	}, { duration: 500, queue: false });
	$('.realModal').animate({
		opacity:1
	}, { duration: 500, queue: false });
	
	$('.modalFadeWindow').css('z-index','101');
	$('.modalContainer').css('z-index','102');
	$('.realModal').css('z-index','103');
}

function changeModalInner(header,bodyString){
	$('.modalHeader').children('h3').html(header);
	$('.modalBody').html(bodyString);
}