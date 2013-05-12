<?
	$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
	socket_connect($socket, '127.0.0.1',2468);
	$buffer = '{
		"action": null,
	    "auth": {
	        "username": "John",
	        "password": "123456"
	    },
	    "data": null
	}';
	socket_write($socket, $buffer, strlen($buffer));
	sleep(1);
	$buf = '';
	if (false !== ($bytes = socket_recv($socket, $buf, 2048, MSG_WAITALL))) {
		echo $buf;
	}
?>