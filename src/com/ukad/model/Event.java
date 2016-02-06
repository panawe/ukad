package com.ukad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ukad.util.Utils;


@Entity
@Table(name = "EVENT")
public class Event extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
	private Long id;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;

	 
	@Column(name = "BEGIN_TIME")
	private Date beginDateTime;
	
	@Column(name ="ALBUM_TAG")
	private String albumTag;
	
	@Column(name="ALBUM_NOTE")
	private String albumNote;

 
	public String getAlbumTag() {
		return albumTag;
	}

	public void setAlbumTag(String albumTag) {
		this.albumTag = albumTag;
	}

	public String getAlbumNote() {
		return albumNote;
	}

	public void setAlbumNote(String albumNote) {
		this.albumNote = albumNote;
	}

	@Column(name = "END_TIME")
	private Date endDateTime;
	
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="CITY")
	private String city;

	@Transient
	private String beginEndDateTime;
	

	public String getBeginEndDateTime() {
		return beginEndDateTime;
	}

	public void setBeginEndDateTime(String beginEndDateTime) {
		this.beginEndDateTime = beginEndDateTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getShortDescription() {
		return description != null && description.length() > 170 ? Utils.truncateHTML(description,170,null) : description;
	}

	@Transient
	public boolean getShowDescriptionLink() {
		return description != null && description.length() > 170 ? true : false;
	}
	
	public Date getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
