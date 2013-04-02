$(window).load(function(){
	$('.modalFadeWindow').click(function(){
		closeModal();
	});
	$('.modalClose').click(function(){
		closeModal();
	});
});

function closeModal(){
	$('.modalFadeWindow').animate({
		opacity: 0
	}, { duration: 500, queue: false });
	$('.modalContainer').animate({
		opacity:0
	}, { duration: 500, queue: false });
	$('.realModal').animate({
		opacity:0
	}, 500,function(){
		$('.modalFadeWindow').css('z-index','-10');
		$('.modalContainer').css('z-index','-10');
		$('.realModal').css('z-index','-10');
	});
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
	
	$('.modalFadeWindow').css('z-index','1001');
	$('.modalContainer').css('z-index','1002');
	$('.realModal').css('z-index','1003');
}

function changeModalInner(header,bodyString){
	$('.modalHeader').children('h3').html(header);
	$('.modalBody').html(bodyString);
}