<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewTagDao" %>
<%@ page import="photoshare.NewTagBean" %>
<%@ page import="java.util.List" %>

<html>
<body>

	<head><title>Add tag</title><head>
	<%	
		String err = "";
		boolean success = false;
		String tag = request.getParameter("tag");
		NewTagDao t = new NewTagDao();
		int picture = Integer.parseInt(request.getParameter("picture"));

		success = t.create(picture,tag);

		if (!success) {
			err = "Please enter in a correct tag";
		}

		if (!err.equals("")) { %>
		<font color=red><b>Error: <%= err %></b></font>
		<p> <a href="picture.jsp?picture_id=<%= picture %>">Go Back</a>
		<% } else { %>

		<h2>Success made a Tag!</h2>
		<a href="picture.jsp?picture_id=<%= picture %>">Go back to picture page</a>

		<% } %>

</body>

</html>