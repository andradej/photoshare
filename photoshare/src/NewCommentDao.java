package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class NewCommentDao {

	private static final String ADD_COMMENT = "INSERT INTO Comments (owner, picture, text, dateofcomment) " +
	"VALUES (?, ?, ?, now())";

	public boolean create(int ownerid, int pictureid, String text) throws SQLException {
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_COMMENT);
			stmt.setInt(1, ownerid);
			stmt.setInt(2, pictureid);
			stmt.setString(3, text);

			stmt.executeUpdate();

			stmt.close();
			conn.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// MEHHHHH MAYBE
	private static final String DISPLAY_ALL_COMMENTS = "SELECT owner, text, dateofcomment FROM Comments WHERE picture = ?";

	public List<NewCommentBean> getPictureComments(int pictureid) {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(DISPLAY_ALL_COMMENTS);
			s.setInt(1, pictureid);
			ResultSet r = s.executeQuery();

			List<NewCommentBean> ret = new ArrayList<NewCommentBean>();

			while (r.next()) {
				String w = r.getString(2);
				if (!w.equals("Like")) {
				NewCommentBean a = new NewCommentBean();
				a.setOwnerId(r.getInt(1));
				a.setText(w);
				a.setDateCommented(r.getDate(3));

				ret.add(a);
			}
			}

			r.close();
			s.close();
			c.close();

			return ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// USED TO GET THE NUMBER OF LIKES
	private static final String GET_NUM_LIKES = "SELECT COUNT(*) FROM Comments WHERE text = 'Like' AND picture = ?";

	public int getNumberLikes(int picid) {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(GET_NUM_LIKES);
			s.setInt(1, picid);
			ResultSet r = s.executeQuery();
			int result = 0;

			while (r.next()) {
				result = (r.getInt(1));
			}

			r.close();
			s.close();
			c.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//USED TO GET USERS WHO LIKED THIS PICTURE
	private static final String GET_USERS_WHO_LIKE = "SELECT owner FROM Comments WHERE text = 'Like' AND picture = ?";

	public List<String> getUsersWhoLiked(int picid) {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(GET_USERS_WHO_LIKE);
			s.setInt(1, picid);
			ResultSet r = s.executeQuery();
			List<String> rs = new ArrayList<String>();
			while (r.next()) {
				rs.add(r.getString(1));
			}

			r.close();
			s.close();
			c.close();

			return rs;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}