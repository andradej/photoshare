<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Top Users</title></head>
<body>
<div style='color:#0000FF'>
    <a href='browse.jsp'>Browse</a>
    <a href='topusers.jsp'>Top Users</a>
    <a href='populartags.jsp'>Popular Tags</a>
    <a href='login.jsp'>Go to Login</a>
</div>

<h1>Top Users on Photoshare</h1>
<% 
	NewUserDao user = new NewUserDao();
	List<NewUserBean> topUser = user.getTopUsers();
%>
<table width="600">
	<tr>
		<td><b>First</b></td>
		<td><b>Last</b></td>
		<td><b>Amount of Contributions</b>
	</tr>

	<%
	for (NewUserBean u : topUser) {
	%>

	<tr>
		<td><%= u.getFirst() %></td>
		<td><%= u.getLast() %></td>
		<td><%= u.getContribution() %></td>
	</tr>
	<% } %>
</table>

</body>
</html>