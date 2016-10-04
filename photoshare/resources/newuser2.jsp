<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Add New User</title></head>


<body>

<h2>Registration Information</h2>

<form action="adduser.jsp" method="post">
  Email: <input type="text" name="email"/><br>
  Password: <input type="password" name="password1"/><br>
  Re-enter password: <input type="password" name="password2"/><br>
  First Name: <input type='text' name='first'/><br>
  Last Name: <input type='text' name='last'/><br>
  Date of Birth: <input type='txt' name='dob' placeholder='mmddyyyyy'/><br>
  <br>
  <em>Optional Information</em><br>
  Gender: <input type='radio' name='gender' value='F'>F<input type='radio' name='gender' value='M'>M<br>
  Hometown: <input type='text' name='hometown'/><br>
  <input type="submit" value="Create"/><br/>
</form>

<br><br>
<a href='login.jsp'>Go back to Login</a>

</body>
</html>
