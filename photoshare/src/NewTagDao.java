package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Arrays;

public class NewTagDao {

	private static final String ADD_TAG = "INSERT INTO Tags (picture, tag) VALUES (?, ?)";

	public boolean create(int pictureid, String tag) throws SQLException {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(ADD_TAG);

			//error checking
			List<String> tokens = Arrays.asList(tag.split("\\s*, \\s*"));

			String singleTag = tokens.get(0);
			s.setInt(1, pictureid);
			s.setString(2, singleTag);
			s.executeUpdate();

			s.close();
			c.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static final String GET_TAGS_FROM_PIC = "SELECT tag FROM Tags WHERE picture = ?";

	public List<String> getTags(int pictureid) throws SQLException {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(GET_TAGS_FROM_PIC);
			s.setInt(1, pictureid);
			ResultSet r = s.executeQuery();

			List<String> ret = new ArrayList<String>();

			while (r.next()) {
				ret.add(r.getString(1));
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

	private static final String GET_POPULAR_TAGS = "SELECT tag, COUNT(picture) as num FROM Tags GROUP BY tag ORDER BY num DESC";

	public List<NewTagBean> getPopularTags() throws SQLException {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(GET_POPULAR_TAGS);
			ResultSet r = s.executeQuery();

			List<NewTagBean> ret = new ArrayList<NewTagBean>();

			while (r.next()) {
				NewTagBean a = new NewTagBean();
				a.setTag(r.getString(1));
				a.setCount(r.getInt(2));
				ret.add(a);
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

	private static final String SEARCH_FOR_TAGS = "SELECT picture FROM Tags WHERE tag = ?";

	public List<Integer> getSearchResults(String tag) {
		try {
			Connection c = DbConnection.getConnection();
			PreparedStatement s = c.prepareStatement(SEARCH_FOR_TAGS);
			s.setString(1, tag);
			ResultSet r = s.executeQuery();

			List<Integer> ret = new ArrayList<Integer>();

			while (r.next()) {
				ret.add(r.getInt(1));
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





}