<?php
//CONNECTION TO THE DATABASE

$url = "localhost";
$username = "zfire";
$password = "....";
$database = "zfire";
$connection = new mysqli($url,$username,$password,$database);
if (mysqli_connect_errno()) {
    die('Could not connect: ' . mysqli_connect_error());
}

?>