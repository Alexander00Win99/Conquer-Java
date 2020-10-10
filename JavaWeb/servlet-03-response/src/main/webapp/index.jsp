<html>
<body>
<h1>Hello World! Welcome Servlet03!</h1>
<%--提交路径--%>
<form action="${pageContext.request.contextPath}/success.jsp" method="get">
    用户名：<input type="text" name="username"></br>
    密码：<input type="password" name="password"></br>
    <input type="submit">
</form>
</body>
</html>
