<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewCommentBean" %>
<%@ page import="photoshare.NewCommentDao" %>
<%@ page import="java.util.List" %>

<html>
<head><title>Add Like</title></head>
<body>
	<%
	NewUserDao owner = new NewUserDao();
	NewCommentDao comment = new NewCommentDao();
	String err = "";
	String typeUser = "";
	int picid = Integer.parseInt(request.getParameter("piclike"));
	String content = request.getParameter("like");


	if (request.getUserPrincipal() == null) {
		typeUser = "anonymous";
	} else {
		typeUser = request.getUserPrincipal().getName();
	}

	int userid;
	if (typeUser.equals("anonymous")) {
		userid = 0;
	} else {
		userid = owner.getUserId(typeUser);
	}

	if (content.length() > 0) {
		boolean success = comment.create(userid, picid, content);
	} else {
		err = "Please enter in comment";
	}

	if (!err.equals("")) { %>
		<font color=red><b>Error: <%= err %></b></font>
		<p> <a href="picture.jsp?picture_id=<%= picid %>">Go Back</a>
	<% } else { %>
	<h2>Success!</h2>
		<a href="picture.jsp?picture_id=<%= picid %>">Go back to picture page</a>
	<% } %>
</body>
</html>