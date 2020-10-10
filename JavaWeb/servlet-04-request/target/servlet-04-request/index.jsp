<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>

<h1>登录</h1>

<div style="text-align: left">
    <form action="${pageContext.request.contextPath}/login" method="post">
        用户：<input type="text" name="username"> <br>
        密码：<input type="text" name="password"> <br>
        爱好：
        篮球<input type="checkbox" name="hobbies" value="篮球">
        网球<input type="checkbox" name="hobbies" value="网球">
        台球<input type="checkbox" name="hobbies" value="台球">
        肉球<input type="checkbox" name="hobbies" value="肉球">
        <br>
        <input type="submit">
    </form>

</div>
</body>
</html>
