package com.services.tct.Parser;

import java.io.Serializable;

public class CategoryItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _name = null;
	private String _singer = null;
	private String _image = null;
	private String _link = null;
	private RSSFeed _children = null;

	private String _date = null;
	private String _id = null;
	private String _cat = null;

	void setName(String name) {
		_name = name;
	}

	void setChildren(RSSFeed children) {

		_children = children;
	}

	void setCat(String cat) {
		_cat = cat;
	}
	
	void setId(String id) {
		_id = id;
	}

	void setSinger(String singer) {
		_singer = singer;
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

	public RSSFeed getChildren() {

		return _children;
	}

	public String getCat() {
		return _cat;
	}
	
	public String getSinger() {
		return _singer;
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
