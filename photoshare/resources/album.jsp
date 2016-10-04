<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewAlbumDao" %>
<%@ page import="photoshare.NewAlbumBean" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>

<jsp:useBean id="imageUploadBean" class="photoshare.ImageUploadBean">
<jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>

<head><title>My Album Page</title><head>

<body>
	<%
	String albumstr = request.getParameter("album_id");
	String albumname = request.getParameter("name");
	int albumid = Integer.parseInt(albumstr);
	NewUserDao user = new NewUserDao();
	String useremail = request.getUserPrincipal().getName();
	int usersid = user.getUserId(useremail);
	%>

	<!-- IF HAVE TIME TRY TO SHOW NAME INTSTEAD -->
	<h1>Contents of Album <%= albumid %></h1>

	<a href="index.jsp">Go back to home page</a><br>

	<h2>Upload a new picture</h2>

	<form action="album.jsp?album_id=<%=albumid %>" enctype="multipart/form-data" method="post">
    	Filename: <input type="file" name="filename"/>
    <input type="submit" value="Upload"/><br/>
</form>

<%
    PictureDao pictureDao = new PictureDao();
    try {
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture, albumid, usersid);
        }
            
    } catch (FileUploadException e) {
        e.printStackTrace();
    }
%>

<h2>Pictures From This Album</h2>
<table>
	<tr>
	<%
		NewAlbumDao albumDao = new NewAlbumDao();
		List<Integer> picid = albumDao.loadPhotos(albumid);
		for (Integer pic : picid) {
	%>
		<td><a href="picture.jsp?picture_id=<%= pic %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pic %>"/>
            <% Picture p= pictureDao.load(pic); %>
            </a>
        </td>
	<% } %>
	</tr>
</table>

</body>
</html>