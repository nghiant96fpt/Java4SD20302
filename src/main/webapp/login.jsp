<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="col-6 offset-3">
			<form action="${pageContext.request.contextPath}/login"
				method="POST">
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Tên tài khoản hoặc email</label>
				  <input value="${bean.usernameOrEmail}" name="usernameOrEmail" type="text" class="form-control">
				  <small class="text-danger">${bean.errors.errUsernameOrEmail}</small>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Mật khẩu</label>
				  <input value="${bean.password}" name="password" type="password" class="form-control">
				  <small class="text-danger">${bean.errors.errPassword}</small>
				</div>
				
				<small class="text-danger">${errLogin}</small>
				<br/>
				
				<button type="submit" class="btn btn-primary">Đăng nhập</button>
			</form>
		</div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>