<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.NewCommentBean" %>
<%@ page import="photoshare.NewCommentDao" %>
<%@ page import="java.util.List" %>

<html>
<head><title>Users Who Liked</title></head>
<body>
<%
	NewUserDao owner = new NewUserDao();
	NewCommentDao comment = new NewCommentDao();
	int picid = Integer.parseInt(request.getParameter("piclike"));
	String content = request.getParameter("like");
%>

<table>
	<tr><b> Owner Ids of People Who Liked this Photo</b><tr>
	<% 
	List<String> users = comment.getUsersWhoLiked(picid);
	for (String u : users) {
	%>
	<tr>
		<td><%= u %></td>
	</tr>
	<% } %>
</table>


</html>
</body>