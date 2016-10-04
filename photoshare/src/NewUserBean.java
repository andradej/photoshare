package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserBean {
  private int user_id = 0; 
  private String email = "";
  private String password1 = "";
  private String password2 = "";
  private String first = "";
  private String last = "";
  private String dob = ""; 
  private String hometown = ""; 
  private String gender = ""; 
  private int contribution = 0;

  public String saySomething() {
    System.out.println("Hello!");
    return "Test";
  }
  
  public int getUserId() {
    return user_id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword1() {
    return password1;
  }

  public String getPassword2() {
    return password2;
  }

  public String getFirst() {
    return first;
  }

  public String getLast() {
    return last;
  }

  public String getDob() { 
    return dob;
  }

  public String getHometown() { 
    return hometown;
  }

  public String getGender() { 
    return gender; 
  }

  public int getContribution() {
    return contribution;
  }

  public void setUserId(int userid) {
    this.user_id = user_id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public void setLast(String last) {
    this.last = last;
  }

  public void setDob(String dob) { 
    this.dob = dob;
  }

  public void setHometown(String hometown) { 
    this.hometown = hometown;
  }

  public void setGender(String gender) { 
    this.gender = gender;
  }

  public void setContribution(int contribution) {
    this.contribution = contribution;
  }
}
