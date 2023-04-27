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
</head>
<body>
	
	<my:navBar></my:navBar>
	<my:alert></my:alert>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">

				<h1>${board.id }번 게시물수정</h1>
				<form method="post">
					<input type="hidden" name="id" value="${board.id }" />
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input type="text" class="form-control" id="titleInput" value=${board.title } name="title"/>
					</div>
					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea name="body" id="bodyTextarea" rows="10" class="form-control" >${board.body }</textarea>
					</div>
					<div class="mb-3">
						<label for="writerInput" class="form-label">작성자</label>
						<input type="text" class="form-control" id="writerInput" value=${board.writer } name="writer"/>
					</div>
					<div class="mb-3">
						<label class="form-label">작성일시</label>
						<input type="text" class="form-control" value=${board.inserted } readonly/>
					</div>
					<div class="mb-3">
						<input class="btn btn-secondary" type="submit" value="수정" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>