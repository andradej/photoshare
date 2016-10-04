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
<head><title>Add new photo to album</title></head>

<body>

<h4>Upload a new photo</h4>

<%
    NewUserDao curruser = new NewUserDao();
    String curruseremail = request.getUserPrincipal().getName();
    int curruserid = curruser.getUserId(curruseremail);
%>

<form action="addpicture.jsp" enctype="multipart/form-data" method="post">
    Filename: <input type="file" name="filename"/>
    <input type="submit" value="Upload"/><br/>
</form>

<%
    PictureDao pictureDao = new PictureDao();
    //try {
        Picture picture = imageUploadBean.upload(request);
        NewAlbumDao albumid = new NewAlbumDao();
        String album_id = request.getParameter("album")));
        //System.out.println(album_id);
        //int album_id = 4
        //if (picture != null) {
            //pictureDao.save(picture,curruserid,album_id);
            //System.out.println("Success! You upload a picture!");
        //}
    //} catch (FileUploadException e) {
        //e.printStackTrace();
    //} finally {
    //}
%>

<form action="index.jsp">
    <input type="submit" value="Go back to My Page">
</form>

</body>
</html>