<?php

$response = array();

if (isset($_POST['nama_kar']) && isset($_POST['alamat_kar']) && isset($_POST['email_kar'])) {

	$nama = $_POST['nama_kar'];
	$alamat = $_POST['alamat_kar'];
	$email = $_POST['email_kar'];

	require_once '../karyawan/db_connect.php';

	$db = new DB_CONNECT();
	$result = mysql_query("INSERT INTO karyawan(nama_kar, alamat_kar, email_kar) VALUES('$nama', '$alamat', '$email')");

	if ($result) {
		$response["success"] = 1;
		$response["message"] = "Successfully Created.";

		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Oops! An error occured.";

		echo json_encode($response);
	}
} else {
	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing";

	echo json_encode($response);
}

?>