<?php
	$con = mysqli_connect("localhost","ozaki0412","gantrithor1!","ozaki0412");

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userGender = $_POST["userGender"];
	$userMajor = $_POST["userMajor"];
	$userAge = $_POST["userAge"];
	$userEmail = $_POST["userEmail"];

	$statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement,"ssssss", $userID , $userPassword, $userGender, $userMajor, $userAge, $userEmail);

	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>