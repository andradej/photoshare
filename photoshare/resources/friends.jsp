<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>

<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Find Friends</title></head>


<body>

<h1>Showing all users</h1>
<a href="index.jsp">Click here to go back to home page</a><br>
<br>

<% 
	NewUserDao curruser = new NewUserDao();
	String curruseremail = request.getUserPrincipal().getName(); //gets user email
	int curruserid = curruser.getUserId(curruseremail);
	NewUserDao friend = new NewUserDao();
	List<NewUserBean> friends = friend.loadUsers(curruserid);
%>

<h4>Search for users (please enter exact email): </h4>
<form action="searchfriends.jsp" method="post">
    <input type="hidden" name="user" value="<%= curruserid %>"/>
    Search user: <input type="text" name="search"/>
    <input type="submit" value="Search"/>
</form>

<table width="600"> 

 	<tr>
 		<td><b>First</b></td>
 		<td><b>Last</b></td>
 		<td><b>Email</b></td>
 		<td><b>Add Friend!</b></td>
 	</tr>

 <% 
 	for (NewUserBean user : friends ) {
 %>

 	<tr>
		<td><%= user.getFirst() %></td> <!-- get all first names -->
		<td><%= user.getLast() %></td> <!-- get all last names -->
		<td><%= user.getEmail() %></td> <!-- get all emails -->

		<td>
            <form action="friends.jsp" method="post">
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
    	String friendemail = request.getParameter("email");
    	int friendid = friend.getUserId(friendemail);
        friend.addFriend(curruserid, friendid); 
    }
%>


</body>
</html>