<?
	$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
	socket_connect($socket, '130.225.196.27',2468);
	$buffer = '{
		"auth": {
			"username": "John",
			"password": "123456"
		},
		"action": "update",
		"data": {
			"type": "user",
			"values": [
				{
					"id": 1,
					"value": {
						"certificate": "fhsduifhiuewuifn49t32ntr2ufni23d",
						"id":1
					}
				}
			]
		}
	}';
	socket_write($socket, $buffer, strlen($buffer));
	sleep(1);
	$buf = '';
	if (false !== ($bytes = socket_recv($socket, $buf, 2048, MSG_WAITALL))) {
		echo $buf;
	}
?>