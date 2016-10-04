package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle the Users table
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserDao {
  private static final String CHECK_EMAIL_STMT = "SELECT " +
      "COUNT(*) FROM Users WHERE email = ?";

  private static final String NEW_USER_STMT = "INSERT INTO " +
      "Users (email, password, first, last, dob, gender, hometown) VALUES (?, ?, ?, ?, ?, ?, ?)";

  public boolean create(String email, String password, String first, String last, String dob, String gender, String hometown) throws SQLException {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        // This email is already in use
        return false; 
      }
      
      try { stmt.close(); }
      catch (Exception e) { }

      stmt = conn.prepareStatement(NEW_USER_STMT);
      stmt.setString(1, email);
      stmt.setString(2, password);
      stmt.setString(3, first);
      stmt.setString(4, last);
      stmt.setString(5, dob);
      stmt.setString(6, gender);
      stmt.setString(7, hometown);
      
      stmt.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
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
  // NEW THINGS ARE PLACED HERE ****************************************************
  // USED TO GET USER ID FROM EMAIL
  private static final String GET_USER_ID_STMT = "SELECT user_id FROM Users WHERE email = ?";

  public int getUserId(String email) throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s = c.prepareStatement(GET_USER_ID_STMT);
      s.setString(1,email);

      ResultSet r = s.executeQuery();

      r.next();
      int result = r.getInt(1);

      r.close(); //turn off result set
      s.close(); //turn off sql statement
      c.close(); //turn off database connection

      return result; //returns the user id

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  // NEED TO DEFINE THE LIST OF ALL USERS

  // STUFF FOR ADDING NEW USERS
  private static final String ADD_FRIEND = "INSERT INTO Friends (user1, user2) VALUES (?, ?)";

  public void addFriend(int curruser, int friend) throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s = c.prepareStatement(ADD_FRIEND);
      s.setInt(1,curruser);
      s.setInt(2,friend);

      s.executeUpdate(); //CHECK ON THIS MORE

      s.close(); //turn off sql statement
      c.close(); //turn off database connection

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  // STUFF FOR LOADING ALL USERS TO SELECT A FRIEND
  private static final String DISPLAY_ALL_USERS = "SELECT first, last, email, user_id FROM Users";
  private static final String GET_USER_INFO = "SELECT first, last, email FROM Users WHERE user_id = ?";
  private static final String GET_FRIENDS_ID = "SELECT user2 FROM Friends WHERE user1 = ?";

  //FIGURE OUT A WAY TO IMPLEMENT NOT SHOWING ALREADY FRIENDS ON THE LIST

  public List<NewUserBean> loadUsers(int userid) throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s = c.prepareStatement(DISPLAY_ALL_USERS);
      ResultSet r = s.executeQuery();

      List<NewUserBean> ret = new ArrayList<NewUserBean>();

      while (r.next()) {
        int e = r.getInt(4);
        if (e != userid && e != 0) {
          NewUserBean a = new NewUserBean();
          a.setFirst(r.getString(1));
          a.setLast(r.getString(2));
          a.setEmail(r.getString(3));

          ret.add(a);
        }
      }
      //WORKING RIGHT HERE ******************************************

      r.close();
      r = null;
      s.close();
      s = null;

      //NEED TO GET THE INTEGERS TO GET FRIEND IDS
      s = c.prepareStatement(GET_FRIENDS_ID);
      s.setInt(1, userid);
      r = s.executeQuery();
      List<Integer> i = new ArrayList<Integer>();
      while (r.next()) {
        i.add(r.getInt(1));
      }

      r.close();
      r = null;
      s.close();
      s = null;

      //NEED TO GET THE INFORMATION OF FRIENDS TO REMOVE
      List<NewUserBean> friend = new ArrayList<NewUserBean>();
      for (Integer b : i) {
        s = c.prepareStatement(GET_USER_INFO);
        s.setInt(1, b);
        r = s.executeQuery();
        while(r.next()) {
          NewUserBean a = new NewUserBean();
          a.setFirst(r.getString(1));
          a.setLast(r.getString(2));
          a.setEmail(r.getString(3));

          ret.remove(a);
        }
        r.close();
        r = null;
        s.close();
        s = null;
      }


      //r.close();
      //s.close();
      c.close();

      return ret;

    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private static final String DISPLAY_ALL_FRIENDS = "SELECT user2 FROM Friends WHERE user1 = ?";

  // STUFF FOR DISPLAYING FRIENDS ON THE INDEX PAGE
  public List<NewUserBean> loadFriends(int userid) throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s = c.prepareStatement(DISPLAY_ALL_FRIENDS);

      s.setInt(1,userid);

      ResultSet r = s.executeQuery();

      List<NewUserBean> ret = new ArrayList<NewUserBean>();

      while (r.next()) {
        PreparedStatement s2 = c.prepareStatement(GET_USER_INFO);
        s2.setInt(1,r.getInt(1));
        ResultSet r2 = s2.executeQuery();

        while (r2.next()) {
          NewUserBean a = new NewUserBean();
          a.setFirst(r2.getString(1));
          a.setLast(r2.getString(2));
          a.setEmail(r2.getString(3));
          ret.add(a);
        }
      }

      r.close();
      s.close();
      c.close();

      return ret;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } 
  }

  //CHECK IF THIS IS RIGHT 
  private static final String TOP_PEEPS = "SELECT U.user_id, U.first, U.last, COUNT(P.owner) as p FROM Users U, Pictures P " +
  "WHERE P.owner = U.user_id  GROUP BY U.user_id ORDER BY p DESC";

  public List<NewUserBean> getTopUsers() throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s = c.prepareStatement(TOP_PEEPS);
      ResultSet r = s.executeQuery();

      List<NewUserBean> ret = new ArrayList<NewUserBean>();

      while (r.next()) {
        NewUserBean a = new NewUserBean();
        a.setUserId(r.getInt(1));
        a.setFirst(r.getString(2));
        a.setLast(r.getString(3));
        a.setContribution(r.getInt(4));

        ret.add(a);
      }

      r.close();
      s.close();
      c.close();

      return ret; 
      
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  private static final String SEARCH_FRIENDS = "SELECT first, last, email FROM Users WHERE email = ?";

  public List<NewUserBean> getSearchResults(String text) throws SQLException {
    try {
      Connection c = DbConnection.getConnection();
      PreparedStatement s = c.prepareStatement(SEARCH_FRIENDS);
      s.setString(1, text);
      ResultSet r = s.executeQuery();

      List<NewUserBean> ret = new ArrayList<NewUserBean>();

      while (r.next()) {
        NewUserBean a = new NewUserBean();
        a.setFirst(r.getString(1));
        a.setLast(r.getString(2));
        a.setEmail(r.getString(3));

        ret.add(a);
      }

      r.close();
      s.close();
      c.close();

      return ret; 
      
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
