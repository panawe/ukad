package com.ukad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PROJECT")
public class Project extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROJECT_ID")
	private Long id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SPONSORS")
	private String sponsors;

	@Column(name ="BUDGET")
	private Double budget;
	
	@Column(name="PROJECTED_START_DATE")
	private Double projectedStartDate;

	@Column(name="PROJECTED_END_DATE")
	private Double projectEndDate;

	@Column(name="START_DATE")
	private Double startDate;

	@Column(name="END_DATE")
	private Double endDate;

	@Column(name="STATUS")
	private Short status;
	
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

	public String getSponsors() {
		return sponsors;
	}

	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getProjectedStartDate() {
		return projectedStartDate;
	}

	public void setProjectedStartDate(Double projectedStartDate) {
		this.projectedStartDate = projectedStartDate;
	}

	public Double getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Double projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public Double getStartDate() {
		return startDate;
	}

	public void setStartDate(Double startDate) {
		this.startDate = startDate;
	}

	public Double getEndDate() {
		return endDate;
	}

	public void setEndDate(Double endDate) {
		this.endDate = endDate;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
