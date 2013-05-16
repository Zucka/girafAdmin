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
	print_r($final);
	echo "</br>";
	$test = mb_convert_encoding($final, "ISO-8859-1");
	print_r($test);
	echo "</br>";
	echo detect($final);
	echo "</br>";
	echo $test["data"][0]["address"];
	echo "</br>";
	$final = iconv('iso-8859-1','utf-8',$final);
	$array = json_decode($final,true);
	print_r($final);
	echo "</br>";
	echo $final["data"][0]["address"];
	echo "</br>";
	$json_errors = array(
    JSON_ERROR_NONE => 'No error has occurred',
    JSON_ERROR_DEPTH => 'The maximum stack depth has been exceeded',
    JSON_ERROR_CTRL_CHAR => 'Control character error, possibly incorrectly encoded',
    JSON_ERROR_SYNTAX => 'Syntax error',
    JSON_ERROR_STATE_MISMATCH => 'Invalid or malformed JSON',
    JSON_ERROR_UTF8 => 'Malformed UTF-8 characters, possibly incorrectly encoded'
	);
 	echo 'Last error : ', $json_errors[json_last_error()], PHP_EOL, PHP_EOL;
	echo "</br>";
	echo $array["data"][0]["address"];
	echo "</br>";
	print_r($array);
	echo "</br>";

	function detect($string, $enc=null) { 
    
	    static $list = array('utf-8', 'iso-8859-1', 'windows-1251');
	    
	    foreach ($list as $item) {
	        $sample = iconv($item, $item, $string);
	        if (md5($sample) == md5($string)) { 
	            if ($enc == $item) { return true; }    else { return $item; } 
	        }
	    }
	    return null;
	}
?>
