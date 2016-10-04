<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>

<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Search Results</title></head>
<body>
<h1> Search Results</h1>
	<%
	int userid = Integer.parseInt(request.getParameter("user"));
	String search = request.getParameter("search");
	NewUserDao friend = new NewUserDao();
	List<NewUserBean> friends = friend.getSearchResults(search);
	%>

<table width="600"> 

 	<tr>
 		<td><b>First</b></td>
 		<td><b>Last</b></td>
 		<td><b>Email</b></td>
 	</tr>

 <% 
 	for (NewUserBean user : friends ) {
 %>

 	<tr>
		<td><%= user.getFirst() %></td> 
		<td><%= user.getLast() %></td> 
		<td><%= user.getEmail() %></td> 
		<td>
            <form action="index.jsp" method="post">
                <input type="hidden" name="action" value="add"/> 
                <input type="hidden" name="email" value="<%= user.getEmail() %>"/>
                <input type="submit" value="Add"/> 
            </form>

        </td>
	</tr>
 <%
 	}
 %>
 </table>

<% 
    if (request.getParameter("action") != null && request.getParameter("action").equals("add")) { // you are calling the input with the name action and getting the parameter of it.
    	int friendid = friend.getUserId(search);
        friend.addFriend(userid, friendid); 
    }
%>

<body>
</html>