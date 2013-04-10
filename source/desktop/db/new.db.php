<?php
$db_api_url = ''; //URL to the db api

/* 
	$json should be the json to be sent NOT ENCODED, i.e. it should be an associative array
	Returns the JSON response NOT ENCODED, i.e. it is an associative array
 */
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
        die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
    }


    curl_close($curl);

    return json_decode($json_response, true);
}

/* Returns a session if authentication was succesful, FALSE otherwise */
function db_getSession($username,$password)
{
	$data = array(
		'auth' => array(
			'username' => $username,
			'password' => $password	
		)
	);

	$result = db_query($data);
	if ($result['status'] != 'OK')
	{
		return FALSE;
	}
	else
	{
		return $result['data']['session']; 
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
function db_insertNewQrCode($session,$userId,$newQr)
{
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

	$result = db_query($data);
	if ($result['status'] != 'OK')
	{
		return FALSE;
	}
	else
	{
		return TRUE;
	}
}
?>