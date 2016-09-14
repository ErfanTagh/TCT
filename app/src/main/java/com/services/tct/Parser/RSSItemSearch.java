package com.services.tct.Parser;

import java.io.Serializable;

public class RSSItemSearch implements Serializable {

	private static final long serialVersionUID = 1L;
	private String _name = null;
	private String _singer = null;
	private String _image = null;
	private String _link = null;

	private String _date = null;
	private String _id = null;

	private RSSFeed _albums = null;
	private RSSFeed _songs = null;
	private RSSFeed _producer = null;

	void setName(String name) {
		_name = name;
	}


	void setSongs(RSSFeed latest) {
		_songs = latest;
	}

	void setProducer(RSSFeed ads) {
		_producer = ads;
	}

	void setAlbums(RSSFeed popular) {
		_albums = popular;
	}

	public RSSFeed getAlbums() {
		return _albums;
	}

	public RSSFeed getProducer() {
		return _producer;
	}

	public RSSFeed getSongs() {
		return _songs;
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
