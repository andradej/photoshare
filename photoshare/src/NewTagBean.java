package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class NewTagBean {
	private int tag_id = 0;
	private String tag = "";
	private int picture = 0;
	private int count = 0;

	public int getCount() {
		return count;
	}

	public int getTagId() {
		return tag_id;
	}

	public String getTag() {
		return tag;
	}

	public int getPicture() {
		return picture;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setTagId(int tag_id) {
		this.tag_id = tag_id;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}
}
