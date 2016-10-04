<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.NewAlbumDao" %>
<%@ page import="photoshare.NewAlbumBean" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head><title>Photoshare</title></head>

<body>
<div style='color:#0000FF'>
    <a href='browse.jsp'>Browse</a>
    <a href='topusers.jsp'>Top Users</a>
    <a href='populartags.jsp'>Popular Tags</a>
</div>

<%
    // used to get current user informmation
    NewUserDao curruser = new NewUserDao();
    String curruseremail = request.getUserPrincipal().getName();
    int curruserid = curruser.getUserId(curruseremail);

    // used to get load album information
    //NewAlbumDao curralbums = new NewAlbumDao();
    //List<NewAlbumBean> myalbums = curralbums.loadMyAlbums(curruserid);
%>
<h1>My Photoshare</h1>

Hello <b><code><%= request.getUserPrincipal().getName()  %></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a>

<!-- SECTION TO DISPLAY ALL PHOTOS THAT BELONG TO USER -->
<br>
<h2>My Pictures</h2>
<table>
    <tr>
    <td>
    <%
    PictureDao myphoto = new PictureDao();
    List<Integer> myPicIds = myphoto.myPictureIds(curruserid);
    for (Integer pictureId : myPicIds) {
    %>
        <td><a href="picture.jsp?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
            <% Picture q = myphoto.load(pictureId); %>
        </a></td>
    <%
        }
    %>
    </td>
    </tr>
</table>


<!-- SECTION TO DISPLAY ALL ALBUMS -->
<br>
<h2>My Albums</h2>
<%
    NewAlbumDao myalbum = new NewAlbumDao();
    List<NewAlbumBean> albums = myalbum.loadMyAlbums(curruserid);
%>

<a href="newalbum.jsp">Create New Album</a>
<br><br>

<table width="600">
    <tr>
        <td><b>Name of Album</b></td>
        <td><b>Date of Creation</b></td>
    </tr>

    <%
        for (NewAlbumBean album : albums) {
    %>

    <tr>
        <td><a href="album.jsp?album_id=<%=album.getAlbumId()%>"><%=album.getName() %></a></td>
        <td><%= album.getDateCreated() %></td>
        <td>
            <form action="index.jsp" method="post">
                <input type="hidden" name="action" value="deleteAlbum"/>
                <input type="hidden" name="albumid" value="<%=album.getAlbumId()%>"/>
                <input type="submit" value="Delete"/>
            </form>
        </td>
    </tr>

    <% } %>
</table>
<%
    if (request.getParameter("action") != null && request.getParameter("action").equals("deleteAlbum")) {
        int albumid = Integer.parseInt(request.getParameter("albumid"));
        myalbum.delete(albumid);
    }
%>

<!-- FINISHED ABOVE SO NOW CHECK THISSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS -->

<!-- SECTION TO DISPLAY ALL FRIENDS -->
<h2>My Friends</h2>

<% 
    List<NewUserBean> friends = curruser.loadFriends(curruserid);
%>

<a href="friends.jsp">Find new Friends</a>
<br><br>

<table width="600">
    <tr>
        <td><b>First</b></td>
        <td><b>Last</b></td>
        <td><b>Email</b></td>
    </tr>

    <% for (NewUserBean myfriends : friends) { %>
    <tr>
        <td><%= myfriends.getFirst() %></td>
        <td><%= myfriends.getLast() %></td>
        <td><%= myfriends.getEmail() %></td>
    </tr>
    <% } %>
</table>

</body>
</html>
