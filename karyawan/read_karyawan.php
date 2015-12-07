<?php

$response = array();

require_once '../karyawan/db_connect.php';

$db = new DB_CONNECT();
$result = mysql_query("SELECT * FROM karyawan") or die(mysql_error());

if (mysql_num_rows($result) > 0) {
	$response["karyawan"] = array();

	while ($row = mysql_fetch_array($result)) {
		$karyawan = array();
		$karyawan["id_kar"] = $row["id_kar"];
		$karyawan["nama_kar"] = $row["nama_kar"];
		$karyawan["alamat_kar"] = $row["alamat_kar"];
		$karyawan["email_kar"] = $row["email_kar"];

		array_push($response["karyawan"], $karyawan);
	}

	$response["success"] = 1;

	echo json_encode($response);
} else {
	$response["success"] = 0;
	$response["message"] = "Tidak ada data Karyawan";

	echo json_encode($response);
}

?>