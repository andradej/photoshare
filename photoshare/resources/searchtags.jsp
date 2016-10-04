<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewTagBean" %>
<%@ page import="photoshare.NewTagDao" %>
<%@ page import="java.util.List" %>

<html>
<head><title>Search Results</title></head>
<body>
	<%
	String search = request.getParameter("search");
	NewTagDao t = new NewTagDao();
	List<Integer> i = t.getSearchResults(search);
	%>
	<h1> Search Results for <%= search %></h1>

	<table>
		<tr>
			<% for (Integer q : i) { %>
		<td><a href="picture.jsp?picture_id=<%= q %>">
            <img src="/photoshare/img?t=1&picture_id=<%= q %>"/></a>
        </td>
        <% } %>
		</tr>
	</table>

</body>
</html>