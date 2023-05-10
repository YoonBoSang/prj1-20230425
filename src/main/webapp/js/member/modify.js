$("#inputPassword, #inputPasswordCheck").keyup(function() {
	$("#wn").addClass("d-none");
	const pw1 = $("#inputPassword").val();
	const pw2 = $("#inputPasswordCheck").val();
	if (pw1 === pw2) {
		$("#modifyButton").removeClass("disabled");
		$("#pwEq").removeClass("d-none");
		$("#pwNeq").addClass("d-none");
	} else {
		$("#modifyButton").addClass("disabled");
		$("#pwEq").addClass("d-none");
		$("#pwNeq").removeClass("d-none");

	}
})