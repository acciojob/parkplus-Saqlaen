package com.driver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Spot {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private int id;
	
	private SpotType spotType;
	private int pricePerHour;
	private boolean ocuupied;
	
	@ManyToOne
	@JoinColumn
	private ParkingLot parkingLot;
	
	@OneToMany(mappedBy = "spot", cascade = CascadeType.ALL )
	List<Reservation> reservationList;

	public Spot(SpotType spotType, int pricePerHour, boolean ocuupied) {
		super();
		this.spotType = spotType;
		this.pricePerHour = pricePerHour;
		this.ocuupied = ocuupied;
	}

	public Spot() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SpotType getSpotType() {
		return spotType;
	}

	public void setSpotType(SpotType spotType) {
		this.spotType = spotType;
	}

	public int getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(int pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public boolean isOcuupied() {
		return ocuupied;
	}

	public void setOcuupied(boolean ocuupied) {
		this.ocuupied = ocuupied;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public List<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	
	
	
	
}
