$("#btn-print").click(function () {
	var printCSS = new String('<link href="../assets/css/style.css" rel="stylesheet" type="text/css">');
	$("#print_frame").contents().find("head").append(printCSS);
	$("#print_frame").contents().find("body").html('<div class="profile-qr"></div>');
	$("#print_frame").contents().find(".profile-qr").load("../../qrgen.php",function(){
		$("#print_frame").focus();
		$("#print_frame")[0].contentWindow.print();
	});
	
});