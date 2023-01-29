package com.driver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private int id;
	
	@Column(columnDefinition = "boolean default false")
	private boolean paymentCompleted;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	@OneToOne
	@JoinColumn
	@JsonIgnoreProperties(value = "payment")
	private Reservation reservation;

	public Payment() {
		super();
	}

	public Payment(boolean paymentComplete, PaymentMode paymentMode) {
		super();
		this.paymentCompleted = paymentComplete;
		this.paymentMode = paymentMode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPaymentCompleted() {
		return paymentCompleted;
	}

	public void setPaymentComplete(boolean paymentCompleted) {
		this.paymentCompleted = paymentCompleted;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentCompleted=" + paymentCompleted + ", paymentMode=" + paymentMode
				+ "]";
	}
	
	

}
