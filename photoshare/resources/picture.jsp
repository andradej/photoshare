<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewAlbumDao" %>
<%@ page import="photoshare.NewAlbumBean" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewCommentBean" %>
<%@ page import="photoshare.NewCommentDao" %>
<%@ page import="photoshare.NewTagBean" %>
<%@ page import="photoshare.NewTagDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>


<html>

<head><title>Picture page</title></head>

<body>

<%
	//gets all the user info
	NewUserDao owner = new NewUserDao();
	String err = "";
	String typeUser = "";
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

	//gets the pic id of the current picture
	int picid = Integer.parseInt(request.getParameter("picture_id"));
%>

 <h1>Picture Details on <%= picid %></h1>

 <a href="index.jsp">Go back to home page</a><br><br>

<img src="/photoshare/img?t=1&picture_id=<%=picid %>"/>

<%
	PictureDao pic = new PictureDao();
	Picture p = pic.load(picid);
%>

<!-- DELETE THE PICTURE -->
<form action="index.jsp" method="post">
    <input type="hidden" name="delete" value="deletepicture"/> 
    <input type="submit" value="Delete"/> 
</form>

<%
if (request.getParameter("delete") != null && request.getParameter("delete").equals("deletepicture")) {
	pic.delete(picid);
}
%>

<!-- IDK HOW TO IMPLEMENT THE LIKES-->
<form action="like.jsp" method="post">
	<input type="hidden" name="piclike" value="<%= picid %>"/>
	<input type="hidden" name="like" value="Like" />
	<input type="submit" value="Like!" />
</form>
<%
	NewCommentDao like = new NewCommentDao();
	int likes = like.getNumberLikes(picid);
%>

<h2>Likes</h2>

<table>
	<tb><%= likes %></tb>
</table>

<form action="userswholike.jsp" method="post">
	<input type="hidden" name="piclike" value="<%= picid %>"/>
	<input type="hidden" name="like" value="Like" />
	<input type="submit" value="Users Who Liked This Photo"/>
</form>


<h2>Tags</h2>
<%
	NewTagDao tag = new NewTagDao();
	List<String> tags = tag.getTags(picid);

	for (String pictag : tags) {
%>
<table>
	<tr><%= pictag %></tr>
</table>
<% } %>

<h4>Add tags to picture</h4>
<form action="tag.jsp" method="post">
	<input type="hidden" name="picture" value="<%= picid %>"/>
	Please enter in a single tag: <input type="text" name="tag"/>
	<input type="submit" value="Add"/>
</form>

<h2>Comments</h2>
<table width="600">
	<tr>
		<td><b>Comment</b></td>
		<td><b>Date</b></td>
		<td><b>Owner Id</b></td>
	</tr>
	<%
	NewUserDao commenter = new NewUserDao();
	NewCommentDao commen = new NewCommentDao();
	List<NewCommentBean> comments = commen.getPictureComments(picid);

	for (NewCommentBean comm : comments) {
	%>
	<tr>
		<td><%= comm.getText() %></td>
		<td><%= comm.getDateCommented() %></td>
		<td><%=comm.getOwnerId() %></td>
	</tr>

	<% } %>
</table>

<% 
	PictureDao pic1 = new PictureDao();
	int pictureowner = pic1.getOwnerOfPic(picid);
	if (pictureowner != userid) {
%>
<h4>Add Comments to Picture</h4>
<form action="comment.jsp" method="post">
	<input type="hidden" name="picture" value="<%= picid %>"/>
	Enter comment: <input type="text" name="comment"/>
	<input type="submit" value="Add"/>
</form>

<% } %>


</body>
</html>
