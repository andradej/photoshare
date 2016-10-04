package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.sql.Date;

/**
* Bean that manages comments
*/

public class NewCommentBean {
	private int comment_id;
	private int owner;
	private int picture;
	private String text;
	private Date dateofcomment;

	public int getCommentId() {
		return comment_id;
	}

	public int getOwnerId() {
		return owner;
	}

	public int getPictureId() {
		return picture;
	}

	public String getText() {
		return text;
	}

	public Date getDateCommented() {
		return dateofcomment;
	}

	public void setCommentId(int comment_id) {
		this.comment_id = comment_id;
	}

	public void setOwnerId(int owner) {
		this.owner = owner;
	}

	public void setPictureId(int picture) {
		this.picture = picture;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDateCommented(Date dateofcomment) {
		this.dateofcomment = dateofcomment;
	}
}