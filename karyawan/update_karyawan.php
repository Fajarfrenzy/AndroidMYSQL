<?php

$response = array();

if (isset($_POST['id_kar']) && isset($_POST['nama_kar']) && isset($_POST['alamat_kar']) && isset($_POST['email_kar'])) {

	$id_kar = $_POST['id_kar'];
	$nama = $_POST['nama_kar'];
	$alamat = $_POST['alamat_kar'];
	$email = $_POST['email_kar'];

	require_once '../karyawan/db_connect.php';

	$db = new DB_CONNECT();
	$result = mysql_query("UPDATE karyawan SET nama_kar='$nama', alamat_kar='$alamat', email_kar='$email' WHERE id_kar = $id_kar");

	if ($result) {
		$response["success"] = 1;
		$response["message"] = "Successfully Updated.";

		echo json_encode($response);
	} else {
		
	}
} else {
	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing";

	echo json_encode($response);
}

?>