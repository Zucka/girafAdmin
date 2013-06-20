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
	    	"type":"profile",
	    	"view":"details",
	    	"ids":[\'1\',\'2\']
	    }
	}';
	socket_write($socket, $buffer, strlen($buffer));
	$buf = '';
	if (false !== ($bytes = socket_recv($socket, $buf, 2048000, MSG_WAITALL))) {
		echo $buf;
	}
?>