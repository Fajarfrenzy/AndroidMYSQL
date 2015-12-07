<?php

$response = array();

if (isset($_POST['id_kar'])) {

	$id_kar = $_POST['id_kar'];

	require_once '../karyawan/db_connect.php';

	$db = new DB_CONNECT();
	$result = mysql_query("DELETE FROM karyawan WHERE id_kar = $id_kar");

	if ($result) {
		$response["success"] = 1;
		$response["message"] = "Successfully Deleted.";

		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Not Found";

		echo json_encode($response);
	}
} else {
	$response["success"] = 0;
	$response["message"] = "Required field(s) is missing";

	echo json_encode($response);
}

?>