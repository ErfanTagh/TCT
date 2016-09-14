package com.services.tct.Parser;

import java.io.Serializable;

public class RSSItemReport implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _name = null;
	private String _image = null;
	private String _link = null;

	private String _date = null;
	private String _id = null;

	void setName(String name) {
		_name = name;
	}

	void setId(String id) {
		_id = id;
	}

	void setLink(String link) {
		_link = link;
	}

	void setDate(String pubdate) {
		_date = pubdate;
	}

	void setImage(String image) {
		_image = image;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getId() {
		return _id;
	}

	public String getLink() {
		return _link;
	}

	public String getDate() {
		return _date;
	}

	public String getImage() {
		return _image;
	}

}
