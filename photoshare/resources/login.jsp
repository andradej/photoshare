<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Photoshare Login</title></head>

<body>
<div style='color:#0000FF'>
	<a href='browse.jsp'>Browse</a>
	<a href='topusers.jsp'>Top Users</a>
	<a href='populartags.jsp'>Popular Tags</a>
</div>

<br></br>

<h2>Please login</h2>

<form method="POST" action="j_security_check">
    <table>
        <tr><th>Email</th><td><input type="text" name="j_username"></td></tr>
        <tr><th>Password</th><td><input type="password" name="j_password"></td></tr>
        <tr><td colspan="2" align="right"><input type="submit" value="Login"/></td></tr>
    </table>
</form>

<br></br>

<h4>New User?</h4>
<form method='link' action='newuser2.jsp'>
	<input type='submit' value='Create new account'>
</form>
<br>

</body>
</html>