<?php
	$con = mysqli_connect("localhost","ozaki0412","gantrithor1!","ozaki0412");

	$userID = $_POST["userID"];

	$statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ?");
	mysqli_stmt_bind_param($statment, "s", $userID);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID);

	$response = array();
	$response["success"] = true;


	while(mysqli_stmt_fetch($statement)){
		$response["success"] = false;
		$response["userID"] = $userID;
	}

	echo json_encode($response);

?>