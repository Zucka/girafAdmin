<?php
session_start();
if (isset($_SESSION['dbsess'])) {$session = $_SESSION['dbsess'];} else {$session = '';}
/* 
	$json should be the json to be sent NOT ENCODED, i.e. it should be an associative array
	Returns the JSON response NOT ENCODED, i.e. it is an associative array
 */
/*
function db_query($json)
{
	global $db_api_url;
    $content = json_encode($json);

    $curl = curl_init($db_api_url);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HTTPHEADER,
            array("Content-type: application/json"));
    curl_setopt($curl, CURLOPT_POST, true);
    curl_setopt($curl, CURLOPT_POSTFIELDS, $content);

    $json_response = curl_exec($curl);

    $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);

    if ( $status != 201 ) {
        die("Error: call to URL $db_api_url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
    }


    curl_close($curl);

    return json_decode($json_response, true);
}
*/
function db_query($json)
{
	$address = '172.25.26.181';
	$port = 2468;
	$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
	socket_connect($socket, '172.25.26.181',2468);
	socket_write($socket, $json, strlen($json));
	sleep(1);
	$buf = '';
	$ret = '';
	if (false !== ($bytes = socket_recv($socket, $buf, 2048, MSG_WAITALL))) {
		$ret .= $buf;
	}
	return json_decode($ret,true);
}
/* Returns an array with session and user(userid) if authentication was succesful, FALSE otherwise */
function db_getSession($username,$password)
{
	$data = '{
		"action": null,
	    "auth": {
	        "username": "'.$username.'",
	        "password": "'.$password.'"
	    },
	    "data": null
	}';

	$result = db_query($data);
	if ($result['status'] != 'OK')
	{
		return FALSE;
	}
	else
	{
		return array('session' => 'asdf','user' => $result['session']['user']); //temporary session
		//return $result['session']['session']; //Uncomment when session is implemented
	}

}

/* 
	gets an array of names of users and their certificates from a list of users using ID
	--inputs--
	session should be a valid DB session
	ids should be an array of ids as ints

	--returns--
	if succesful, returns an associative array of names and their certificate
	if failure, returns FALSE
 */
function db_getCertificatesFromIds($session,$ids)
{
	global $session;
	$dataUser = array(
		'auth' => array(
			'session' => $session
		),
		'action' => 'read',
		'data' => array(
			'type' => 'user',
			'view' => 'list',
			'ids' => $ids
		)
	);
	$resultUser = db_query($dataUser);
	if ($resultUser['status'] != 'OK')
	{
		return FALSE;
	}

	$dataProfile = array(
		'auth' => array(
			'session' => $session
		),
		'action' => 'read',
		'data' => array(
			'type' => 'profile',
			'view' => 'list',
			'ids' => $ids
		)
	);

	$resultProfile = db_query($dataProfile);
	if ($resultProfile['status'] != 'OK')
	{
		return FALSE;
	}
	$result = array();
	$count = count($resultUser['data']);
	for ($i=0; $i < $count; $i++) { 
		$id = $resultUser['data'][i]['id'];
		$name = '';
		foreach ($resultProfile['data'] as $key => $value) {
			if ($value['id'] == $id)
			{
				$name = $value['name'];
				break;
			}
		}
		$result[] = array(
			'name' => $name,
			'certificate' => $resultUser['data'][i]['certificate']
		);
	}

	return $result;
}
/*
	Inserts a new qr code for a user

	--Inputs--
	$session should be a valid DB session
	$userId should be the user to be updated
	$newQr should be a 512 character unique string 

*/
function db_insertNewQrCode($userId,$newQr)
{
	global $session;
	$data = array(
		'auth' => array(
			'session' => $session
		),
		'action' => 'update',
		'data' => array(
			'type' => 'user',
			'values' => array(
				array(
					'id' => $userId,
					'value' => array(
						'certificate' => $newQr
					)
				)
			)
		)
	);

	$result = db_query(json_encode($data));
	error_log($result['status']);
	if ($result['status'] == 'OK')
	{
		return TRUE;
	}
	else
	{
		return FALSE;
	}
}
?>