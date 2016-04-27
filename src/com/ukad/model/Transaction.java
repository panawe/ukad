package com.ukad.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ukad.security.model.User;
import com.ukad.model.PaymentHistory;

@Entity
@Table(name = "TRANSACTION")
public class Transaction extends BaseEntity {

	public Transaction(){super();}
	
	public Transaction(PaymentHistory pay){
		io=1;
		amount=pay.getAmount();
		comment=pay.getDescription();
		rebate=0D;
		paymentType=pay.getPaymentType();
		user=pay.getUser();
		Calendar c = Calendar.getInstance();     
		year=new Date().getYear();
		month=c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE ) ;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSACTION_ID")
	private Long id;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "REBATE")
	private Double rebate;

	@Column(name = "MONTH_PAID")
	private String month;

	@Column(name = "YEAR_PAID")
	private Integer year;

	@Column(name = "IO")
	private Short io;

	@Column(name = "COMMENT")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "PAYMENT_TYPE_ID")
	private PaymentType paymentType;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Short getIo() {
		return io;
	}

	public void setIo(Short io) {
		this.io = io;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
