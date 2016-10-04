<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import='photoshare.NewAlbumDao' %>
<%@ page import='photoshare.NewUserDao' %>

<!-- THIS ONE IS FOR ADDING ALBUMS TO THE DATABASE -->
<!-- AUTHOR: JACKIE ANDRADE -->

<html>
<head><title>Adding New Album</title></head>

<body>
<%
	String err = null;
	String name = request.getParameter("name");
	String email = request.getUserPrincipal().getName();

	if (name.length() > 0 || !name.equals("")) {
		NewAlbumDao album = new NewAlbumDao();
		NewUserDao user = new NewUserDao();

		int owner = user.getUserId(email);
		boolean success = album.create(name, owner);
		if (!success) {
			err = "Could not create new album!";
		} 
	} else {
		err = "Please give an album name";
	}
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="newalbum.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>A new album has been created! <a href="index.jsp">Go back</a>.

<% } %>
</body>
</html>
