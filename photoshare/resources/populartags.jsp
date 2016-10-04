<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewTagBean" %>
<%@ page import="photoshare.NewTagDao" %>
<%@ page import="java.util.List" %>

<html>
	<head><title>Popular Tags</title></head>
<body>

<div style='color:#0000FF'>
	<a href='browse.jsp'>Browse</a>
	<a href='topusers.jsp'>Top Users</a>
	<a href='populartags.jsp'>Popular Tags</a>
	<a href='login.jsp'>Go to Login</a>
</div>

<h1>Popular Tags on Photoshare</h1>

<h4>Search for tags (please enter exact tag): </h4>
<form action="searchtags.jsp" method="post">
    Search tags: <input type="text" name="search"/>
    <input type="submit" value="Search"/>
</form>

<%
	NewTagDao t1 = new NewTagDao();
	List<NewTagBean> t2 = t1.getPopularTags();
%>

<table width="600">

	<tr>
		<td><b>Tag</b></td>
		<td><b>Number of Pictures With This Tag</b></td>
	</tr>

	<%
	for (NewTagBean tag : t2) {
	%>

	<tr>
		<td><%= tag.getTag() %></td>
		<td><%= tag.getCount() %></td>
	</tr>

	<% } %>

</table>

	
</body>
</html>
