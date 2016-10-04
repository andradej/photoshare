package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.sql.*;

/**
 * A bean that handles new album data
 */

public class NewAlbumBean {
	private int album_id;
	private String name = "";
	private int owner;
	private Date date_created;

	public int getAlbumId() {
		return album_id;
	}

	public String getName() {
		return name;
	}

	public int getOwner() {
		return owner;
	}

	public Date getDateCreated() {
		return date_created;
	}

	public void setAlbumId(int album_id) {
		this.album_id = album_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public void setDateCreated(Date date_created) {
		this.date_created = date_created;
	}
}