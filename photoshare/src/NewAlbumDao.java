package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * A data access object (DAO) to handle the Albums table
 */

public class NewAlbumDao {

	private static final String CREATE_NEW_ALBUM = "INSERT INTO Albums (name, owner, date_created) " +
		"VALUES (?, ?, now())";

	public boolean create(String name, int owner) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
      		conn = DbConnection.getConnection();
      		stmt = conn.prepareStatement(CREATE_NEW_ALBUM);
      		stmt.setString(1, name);
      		stmt.setInt(2, owner);
      
      		stmt.executeUpdate();

      		return true;
    	} catch (SQLException e) {
      		e.printStackTrace();
      		throw new RuntimeException(e);
    	} finally {
      		if (stmt != null) {
        		try { stmt.close(); }
       			catch (SQLException e) { ; }
        		stmt = null;
      		}
      
      		if (conn != null) {
        		try { conn.close(); }
        		catch (SQLException e) { ; }
       	 		conn = null;
      		}
    	}
    }
  
  // THIS IS NOT WORKINGGGGGG**********************************
  private static final String DELETE_ALBUMS = "DELETE FROM Albums WHERE album_id = ?";

  //THIS ONE NEEDS TO BE WORKED ON
  private static final String DELETE_PIC_FROM_ALBUMS = "DELETE FROM Pictures WHERE picture_id = ?";

  public void delete(int albumid) throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s1 = null;
      
      List<Integer> l = loadPhotos(albumid);
      for (Integer i : l) {
        s1 = c.prepareStatement(DELETE_PIC_FROM_ALBUMS);
        s1.setInt(1,i);
        s1.executeUpdate();
        s1.close();
        s1 = null;
      }

      PreparedStatement s2 = c.prepareStatement(DELETE_ALBUMS);
      s2.setInt(1,albumid);
      s2.executeUpdate();
      s2.close();
      c.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private static final String MY_ALBUMS = "SELECT name, owner, date_created, album_id FROM Albums WHERE owner = ?";

  public List<NewAlbumBean> loadMyAlbums(int owner) throws SQLException {
    try {
      Connection conn = DbConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement(MY_ALBUMS);
      stmt.setInt(1,owner);
      ResultSet r = stmt.executeQuery();
      List<NewAlbumBean> ret = new ArrayList<NewAlbumBean>();

      while (r.next()) {
        NewAlbumBean a = new NewAlbumBean();
        a.setName(r.getString(1));
        a.setOwner(r.getInt(2));
        a.setDateCreated(r.getDate(3));
        a.setAlbumId(r.getInt(4));
        ret.add(a);
      }

      r.close();
      stmt.close();
      conn.close();

      return ret;

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private static final String GET_ALBUM_ID = "SELECT album_id FROM Albums WHERE name = ?";

  public int getAlbumId(String name) throws SQLException {
    try {
      Connection conn = DbConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement(GET_ALBUM_ID);
      stmt.setString(1,name);
      ResultSet r = stmt.executeQuery();

      r.next();
      int result = r.getInt(1);

      r.close();
      r = null;
      stmt.close();
      stmt = null;
      conn.close();
      conn = null;

      return result;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static final String GET_PICTURES_FROM_ALBUM = "SELECT picture_id FROM picandalbum WHERE album_id = ?";
  public List<Integer> loadPhotos(int albumid) {
    try {
      Connection conn = DbConnection.getConnection();
      PreparedStatement stmt = conn.prepareStatement(GET_PICTURES_FROM_ALBUM);
      stmt.setInt(1,albumid);
      ResultSet r = stmt.executeQuery();

      List<Integer> ret = new ArrayList<Integer>();

      while (r.next()) {
        ret.add(r.getInt(1));
      }

      r.close();
      stmt.close();
      conn.close();

      return ret;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}