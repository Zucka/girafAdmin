<?php
	session_start();
	if (isset($_GET['lang'])) {$lang = $_GET['lang'];} else {$lang = 'en';}
	//INCLUDE LANG FILES (GET PARAMETER FOR NOW, ADD AUTOMATIC?)
	switch ($lang) {
		case 'en':
			include('../assets/lang/create_profile/create_profile.en.php');
			break;
		case 'dk':
			include('assets/lang/create_profile/create_profile.dk.php');
			break;
		default:
			include('assets/lang/create_profile/create_profile.en.php');
			break;
	}
	
?>

<?php
echo '
	<html lang="en">
		<head>
			<meta charset="utf-8">
			<title>Create Relation</title>
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
	    	<meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- Force document mode to IE9 standards -->
	    	<meta name="description" content="">
	    	<meta name="author" content="">

	    	<!-- Stylesheets -->
			<link href="assets/css/bootstrap.css" rel="stylesheet">
			<link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
			<link href="assets/css/style.css" rel="stylesheet">

			<!-- Fav and touch icons -->
			<link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
			<link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
			<link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
			<link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
			<link rel="shortcut icon" href="../assets/ico/favicon.ico">
		</head>
		<body>
			<div align="center">
				<br>
				<span class="breadcrump"> </span>
				<br>
			</div>
				
			<div class="container-fluid row">
				<div class="span4 offset1">
					<h3> Børn</h3>
					<table class="table table-striped">
						<tr>
							<th>Navn</th>
							<th class="span1">Tilføj Relation</th>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
						<tr>
							<td>Hans Jensen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
					</table>
				</div>
				<div class="span4 offset2">
					<h3> Forældrer og Pædagoger</h3>
					<table class="table table-striped">
						<tr>
							<th>Navn</th>
							<th class="span1">Tilføj Relation</th>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
						<tr>
							<td>Hans Jensen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
						<tr>
							<td>Pia Larsen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
						<tr>
							<td>Jens Hansen</td>
							<td>
								<label class="checkbox inline">
									<input type="checkbox" id="inlineCheckbox1" value="option1">
								</label>
							</td>
						</tr>
					</table>
				</div>
			</div>
				
			<div align="center">
				<button class="btn btn-large" type="button"><i class="icon-wrench" ></i> Opret Relation</button>
			</div>
		</body>
	</html>'
	

?>