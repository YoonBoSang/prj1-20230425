<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<my:navBar></my:navBar>
	<my:alert></my:alert>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>회원 가입</h1>
				<form method="post">
					<!-- 					.mb-3*5>(label.form-label[for]+input.form-control[name]) -->
					<div class="mb-3">
						<label for="inputId" class="form-label">ID</label>
						<div class="input-group">
							<input id="inputId" type="text" class="form-control" name="id" value="${member.id }" />
							<button type="button" id="checkIdBtn" class="btn btn-outline-secondary">중복확인</button>
						</div>

						<div id="availableIdMessage" class="form-text d-none text-primary">
							<i class="fa-solid fa-check"></i>
							사용가능한 ID입니다.
						</div>
						<div id="notAvailableIdMessage" class="form-text d-none text-danger">
							<i class="fa-solid fa-triangle-exclamation"></i>
							사용 불가능한 ID입니다.
						</div>

					</div>
					<div class="mb-3">
						<label for="inputPassword" class="form-label">PASSWORD</label>
						<input type="password" class="form-control" name="password" id="inputPassword" />
					</div>
					<div class="mb-3">
						<label for="inputPasswordCheck" class="form-label">PASSWORD CHECK</label>
						<input type="password" class="form-control" id="inputPasswordCheck" />
					</div>
					<div class="form-text d-none text-primary" id="passwordSuccessText">
						<i class="fa-solid fa-check"></i>
						패스워드 일치
					</div>
					<div class="form-text d-none text-danger" id="passwordFailText">
						<i class="fa-solid fa-triangle-exclamation"></i>
						패스워드 불일치
					</div>
					<div class="mb-3">
						<label for="inputNickName" class="form-label">NICKNAME</label>
						<div class="input-group">
							<input type="text" class="form-control" name="nickName" id="inputNickName" value="${member.nickName }" />
							<button type="button" id="checkNickNameBtn" class="btn btn-outline-secondary">중복확인</button>
						</div>

						<div id="availableNickNameMessage" class="form-text d-none text-primary">
							<i class="fa-solid fa-check"></i>
							사용 가능한 별명입니다.
						</div>
						<div id="notAvailableNickNameMessage" class="form-text d-none text-danger">
							<i class="fa-solid fa-triangle-exclamation"></i>
							사용 불가능한 별명입니다.
						</div>

					</div>
					<div class="mb-3">
						<label for="inputEmail" class="form-label">Email</label>
						<div class="input-group">
							<input type="email" class="form-control" name="email" id="inputEmail" value="${member.email }" />
							<button type="button" id="checkEmailBtn" class="btn btn-outline-secondary">중복확인</button>
						</div>

						<div id="availableEmailMessage" class="form-text d-none text-primary">
							<i class="fa-solid fa-check"></i>
							사용 가능한 이메일입니다.
						</div>
						<div id="notAvailableEmailMessage" class="form-text d-none text-danger">
							<i class="fa-solid fa-triangle-exclamation"></i>
							사용 불가능한 이메일입니다.
						</div>
					</div>
					<div class="mb-3">
						<input type="submit" class="btn btn-primary" name="가입" id="signupSubmit" disabled />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/js/member/signup.js"></script>


</body>
</html>