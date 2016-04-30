package com.ukad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ukad.util.Utils;

@Entity
@Table(name = "WEB_LINK")
public class Weblink extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WEB_LINK_ID")
	private Long id;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "STATUS")
	private Short status;
	
	@JsonFormat(pattern = "MM/dd/yyyy")
	@Column(name = "BEGIN_DATE")
	private Date beginDate;

	@JsonFormat(pattern = "MM/dd/yyyy")
	@Column(name = "END_DATE")
	private Date endDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String getStatusDescription() {
		String desc = "";
		if (status == 0)
			desc = "Active";
		else if (status == 1)
			desc = "Desactive";
		
		return desc;
	}
}
