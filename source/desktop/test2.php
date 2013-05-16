<?
	$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
	socket_connect($socket, '130.225.196.27',2468);
	$buffer = '{
		"action": "read",
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": {
	    	"type":"department",
	    	"view":"details",
	    	"ids":[1]
	    }
	}';
	socket_write($socket, $buffer, strlen($buffer));
	sleep(1);
	$buf = '';
	$final = '';
	if (false !== ($bytes = socket_recv($socket, $buf, 2048, MSG_WAITALL))) {
		$final .= $buf;
	}
	$array = json_decode($final,true);
	echo $final;
	print_r($array);
	echo $array['data'][0]['address'];
	echo '<br><br><br>';
	$data = '
		{"data":[{"address":"Selma Lagerløfs Vej 300","email":"test@test.com","id":1,"name":"Cassiopeia","phone":"12345678","subdepartments":[],"topdepartment":0}],"errors":[],"session":{"profile":1,"session":"","user":1},"status":"OK"}
	';
	$test = json_decode($data,true);
	print_r($test);
?>