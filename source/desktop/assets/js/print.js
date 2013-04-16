$(window).load(function () {
	$("#btn-print").click(function () {
		var printCSS = new String('<link href="../assets/css/style.css" rel="stylesheet" type="text/css">');
		$("#print_frame").contents().find("head").append(printCSS);
		$("#print_frame").contents().find("body").html('<div class="profile-qr"></div>');
		$("#print_frame").contents().find(".profile-qr").load("../../qrgen.php",function(){
			$("#print_frame").focus();
			$("#print_frame")[0].contentWindow.print();
		});
		
	});

	$("#btn-gen").click(function(){
		$.ajax({
			url: "sites/generateNewQr.php"
		}).done(function(data){
			if (data.status = "ok")
			{
				$("#profile-qr-status").css({"color":"green"}).html("Successfully generated new QR");
				$("#profile-qr-object").attr('data','../qrgen.php');
			}
			else if (data.status = "error")
			{
				$("#profile-qr-status").css({"color":"red"}).html("Failed generating new QR, try refreshing");
			}
		});
	});
});

