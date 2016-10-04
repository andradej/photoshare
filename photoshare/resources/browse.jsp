<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
	<head><title>Showing All Photos</title></head>
<body>
<div style='color:#0000FF'>
    <a href='browse.jsp'>Browse</a>
    <a href='topusers.jsp'>Top Users</a>
    <a href='populartags.jsp'>Popular Tags</a>
    <a href='login.jsp'>Go to Login</a>
</div>

<h1>Welcome to Photoshare! Now showing all available photos!</h1>

<h2>All Pictures</h2>
<table>
    <tr>
        <%	
        	PictureDao pictureDao = new PictureDao();
            List<Integer> pictureIds = pictureDao.allPicturesIds();
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="picture.jsp?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
        </a>
        </td>
        <%
            }
        %>
    </tr>
</table>
</body>
</html>