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
						"certificate": "80d6548b5e13aa616830f8328771b827d4665174760a85504f477107be0292a4483d964aee484d9bbb67e43a22e0b8bf3d1bddb66239cbe556dad85ef0713a91d3aa402f7d84e7e88a1ccd783da1b9f0af5a540a0d5da1b5cb418f1e9ccaa3b4e113f5e0b16fdd6aa253fc52de712171b93bb7d07d6adddafca5526f703b2d87b48e0b4a686319cf6d7386dd2df8c11e6a844816caeaecb362c945f8ef403cfb0b99595564af3bf05eb632dabe3fc856c189598b28e654f9fa9614698d8edc12357c13cd004a58c018031c42243ae6e3ff63dda34d80ec780449ae79201df48f460fc5ab79769cac9e1f0b6dd69e8c929605e3db1dac4a7da5f0015282ed3ed7",
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