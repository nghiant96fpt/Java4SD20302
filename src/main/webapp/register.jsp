<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<style>
err{
	color: red;
	font-size: 14px
}
</style>
</head>
<body>
	<div class="container">
		<div class="col-6 offset-3">
			<form method="POST"
				action="${pageContext.request.contextPath}/register">
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Username</label>
				  <input value="${bean.username}" name="username" type="text" class="form-control">
				  <small class="text-danger">${bean.errors.errUsername}</small>
				  <small class="text-danger">${errDB.errUsername }</small>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Mật khẩu</label>
				  <input value="${bean.password}" name="password" type="password" class="form-control">
				  <small class="text-danger">${bean.errors.errPassword}</small>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Họ và tên</label>
				  <input value="${bean.name}" name="name" type="text" class="form-control">
				  <small class="text-danger">${bean.errors.errName}</small>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Email</label>
				  <input value="${bean.email}" name="email" type="text" class="form-control">
				  <small class="text-danger">${bean.errors.errEmail}</small>
				  <small class="text-danger">${errDB.errEmail }</small>
				</div>
				
				<div class="mb-3">
				  <label for="exampleFormControlInput1" class="form-label">Số điện thoại</label>
				  <input value="${bean.phone}" name="phone" type="text" class="form-control">
				  <small class="text-danger">${bean.errors.errPhone}</small>
				  <small class="text-danger">${errDB.errPhone }</small>
				</div>
				
				<button type="submit" class="btn btn-primary">Đăng ký</button>
			</form>
		</div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>