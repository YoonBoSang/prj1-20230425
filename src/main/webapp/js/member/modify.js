let checkEmail = true;
let checkNickame = true;
let checkPassword = true;

function enableSubmit() {
	if (checkEmail && checkNickame && checkPassword) {
		$("#modifyButton").removeAttr("disabled");
	} else {
		$("#modifyButton").attr("disabled", "");
	}
}

$("#inputNickName").keyup(function() {
	// 별명 중복확인 다시
	checkNickName = false;
	$("#availableNickNameMessage").addClass("d-none")
	$("#notAvailableNickNameMessage").addClass("d-none")
	
	// submit 버튼 비활성화
	enableSubmit();
})

$("#inputEmail").keyup(function() {
	checkEmail = false;
	$("#availableEmailMessage").addClass("d-none");
	$("#notAvailableEmailMessage").addClass("d-none");
	enableSubmit();
});

// 닉네임 중복확인버튼 클릭시
$("#checkNickNameBtn").click(function() {
	const nickName = $("#inputNickName").val();
	$.ajax("/member/checkNickName/" + nickName, {
		success: function(data) {
			// `{"available" : true}`
			if (data.available) {
				// 사용가능하다는 메세지 출력
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");
				checkEmail = true;
				// 중복확인 되었다는 표시
			} else {
				// 사용 불가능 하다는 메세지 출력
				$("#availableNickNameMessage").addClass("d-none");
				$("#notAvailableNickNameMessage").removeClass("d-none");
				checkEmail = false;
				// 중복확인 안되었다는 표시
			}
		},
		complete: enableSubmit
		//		complete: // 수정버튼 활성화/비활성화
	});
});

// 이메일 중복확인 버튼이 클릭되면
$("#checkEmailBtn").click(function() {
	const email = $("#inputEmail").val();
	var regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/;
	$.ajax("/member/checkEmail/" + email, {
		success: function(data) {
			
			if (data.available && regExp.test(email)) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
				checkNickame = true;
			} else {
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
				checkNickame = false;
			}
		},
		complete: enableSubmit
	});
});


$("#inputPassword, #inputPasswordCheck").keyup(function() {
	$("#wn").addClass("d-none");
	const pw1 = $("#inputPassword").val();
	const pw2 = $("#inputPasswordCheck").val();
	if (pw1 === pw2) {
		$("#modifyButton").removeClass("disabled");
		$("#pwEq").removeClass("d-none");
		$("#pwNeq").addClass("d-none");
		checkPassword = true;
	} else {
		$("#modifyButton").addClass("disabled");
		$("#pwEq").addClass("d-none");
		$("#pwNeq").removeClass("d-none");
		checkPassword = false;
	}
})