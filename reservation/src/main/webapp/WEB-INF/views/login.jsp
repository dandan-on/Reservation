<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<form method="post" action="/reservation/authenticate">
			<div>
				<label>Email : </label>
				<input type="text" name="email">
			</div>
			<div>
				<label>Password : </label>
				<input type="password" name="password">
			</div>
			<div>
				<input type="submit" value="로그인">
			</div>
		</form>	
	</div>
</body>
</html>