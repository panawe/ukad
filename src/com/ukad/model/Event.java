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
public class Event extends BaseEntity implements Comparable<Event> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "BEGIN_TIME")
	private Date startsAt;

	@Column(name = "ALBUM_TAG")
	private String albumTag;

	@Column(name = "ALBUM_NOTE")
	private String albumNote;
	@Transient
	private String type = "info";
	@Column(name = "REPORT")
	private String report;
	@Transient
	private boolean hasPhoto;
	@Transient
	private boolean hasReport;
	@Transient
	private boolean hasYoutube=false;
	@Transient
	private String videoId;
	
	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public boolean isHasYoutube() {
		return hasYoutube;
	}

	public void setHasYoutube(boolean hasYoutube) {
		this.hasYoutube = hasYoutube;
	}

	public boolean isHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public boolean isHasReport() {
		return hasReport;
	}

	public void setHasReport(boolean hasReport) {
		this.hasReport = hasReport;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
	private Date endsAt;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
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
		return description != null && description.length() > 170 ? Utils.truncateHTML(description, 170, null)
				: description;
	}

	@Transient
	public boolean getShowDescriptionLink() {
		return description != null && description.length() > 170 ? true : false;
	}

	public Date getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(Date startsAt) {
		this.startsAt = startsAt;
	}

	public Date getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(Date endsAt) {
		this.endsAt = endsAt;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int compareTo(Event another) {
		if (this.getStartsAt().before(another.getStartsAt())) {
			return -1;
		} else {
			return 1;
		}
	}
}
