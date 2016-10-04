<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- THIS IS THE PAGE TO MAKE NEW ALBUMS -->
<!-- AUTHOR: JACKIE ANDRADE -->

<html>
<head><title>Add New Album</title></head>

<body>
	<h2>New Album Info</h2>
	<form action='addalbum.jsp' method='post'>
		Name of New Album: <input type='text' name='name'><br>
		<input type='submit' value='Create'/><br>
	</form>

	<br><br>
	<a href='index.jsp'>Go back to my home page</a>
</body>
</html>