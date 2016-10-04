package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PictureDao {
  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\" FROM Pictures WHERE \"picture_id\" = ?";
  
  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures ORDER BY \"picture_id\" DESC";

  public Picture load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      stmt.setInt(1, id);
			rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
      }

			rs.close();
			rs = null;
		
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return picture;
	}

	private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "Pictures (\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"owner\") VALUES (?, ?, ?, ?, ?, ?)";

    private static final String LAST_INSERTED = "SELECT picture_id FROM Pictures ORDER BY picture_id DESC LIMIT 1";

    private static final String SAVE_ALBUMPIC_REL = "INSERT INTO picandalbum (album_id, picture_id) VALUES (?, ?)";

	public void save(Picture picture, int albumid, int owner) throws SQLException { //int owner, int albumid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet r = null;
		int picid = 0;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
			stmt.setString(1, picture.getCaption());
			stmt.setBytes(2, picture.getData());
			stmt.setBytes(3, picture.getThumbdata());
			stmt.setLong(4, picture.getSize());
			stmt.setString(5, picture.getContentType());
			stmt.setInt(6, owner);
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;

			stmt = conn.prepareStatement(LAST_INSERTED);
			r = stmt.executeQuery();
			while (r.next()) {
				picid = r.getInt(1);
			}

			r.close();
			r = null;

			stmt.close();
			stmt = null;

			stmt = conn.prepareStatement(SAVE_ALBUMPIC_REL);
			stmt.setInt(1, albumid);
			stmt.setInt(2, picid);
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public List<Integer> allPicturesIds() throws SQLException {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return picturesIds;
	}

	public static final String MY_PICTURE_IDS = "SELECT picture_id FROM Pictures WHERE owner = ?";

	public List<Integer> myPictureIds(int owner) throws SQLException {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(MY_PICTURE_IDS);
			s.setInt(1, owner);
			ResultSet r = s.executeQuery();

			List<Integer> pictid = new ArrayList<Integer>();

			while (r.next()) {
				pictid.add(r.getInt(1));
			}

			r.close();
			s.close();
			c.close();

			return pictid;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static final String DELETE_PICTURE = "DELETE FROM Pictures WHERE picture_id = ?";
	private static final String DELETE_ALBUMPIC_LINK = "DELETE FROM picandalbum WHERE picture_id = ?";
	private static final String DELETE_PIC_COMMENTS = "DELETE FROM Comments WHERE picture = ?";

	public void delete(int picid) throws SQLException {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s3 = c.prepareStatement(DELETE_PICTURE);
			s3.setInt(1,picid);
			s3.executeUpdate();
			s3.close();

			PreparedStatement s1 = c.prepareStatement(DELETE_PIC_COMMENTS);
			s1.setInt(1, picid);
			s1.executeUpdate();
			s1.close();

			PreparedStatement s2 = c.prepareStatement(DELETE_ALBUMPIC_LINK);
			s2.setInt(1, picid);
			s2.executeUpdate();
			s2.close();

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static final String ALL_MY_PICTURES = "SELECT picture_id FROM Pictures WHERE owner = ?";
	public List<Integer> loadMyPictures(int owner) throws SQLException {
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(ALL_MY_PICTURES);
			stmt.setInt(1,owner);
			ResultSet rs = stmt.executeQuery();

			List<Integer> pictureIds = new ArrayList<Integer>();

			while (rs.next()) {
				pictureIds.add(rs.getInt(1));
			}

			rs.close();
			stmt.close();
			conn.close();

			return pictureIds;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

	private static final String GET_OWNER_FROM_PIC = "SELECT owner FROM Pictures WHERE picture_id = ?";

	public int getOwnerOfPic(int picid) {
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_OWNER_FROM_PIC);
			stmt.setInt(1,picid);
			ResultSet rs = stmt.executeQuery();

			rs.next();

			int result = rs.getInt(1);

			rs.close();
			stmt.close();
			conn.close();

			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
}
