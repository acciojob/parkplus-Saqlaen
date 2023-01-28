package com.driver.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private int id;
	
	private int numberOfHours;
	
	private int billAmount;
	
	public int getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}

	@ManyToOne
	@JoinColumn
	private User user;
	
	@ManyToOne
	@JoinColumn
	private Spot spot;
	
	@OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
	private Payment payment;

	public Reservation() {
		super();
	}

	public Reservation(int numberOfHours) {
		super();
		this.numberOfHours = numberOfHours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfHours() {
		return numberOfHours;
	}

	public void setNumberOfHours(int NumberOfHours) {
		this.numberOfHours = NumberOfHours;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	public Payment getPayment() {
		return payment;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", numberOfHours=" + numberOfHours + ", billAmount=" + billAmount + ", user="
				+ user + ", spot=" + spot + ", payment=" + payment + "]";
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	

}
