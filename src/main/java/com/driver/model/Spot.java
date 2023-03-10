package com.driver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Spot {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private int id;
	
	@Enumerated(EnumType.STRING)
	private SpotType spotType;
	
	private int pricePerHour;
	
	@Column(columnDefinition = "boolean default false")
	private boolean occupied;
	
	public boolean isOccupied() {
		return occupied;
	}

	public boolean getOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	@ManyToOne
	@JoinColumn
	@JsonIgnoreProperties(value = "spotList")
	private ParkingLot parkingLot;
	
	@OneToMany(mappedBy = "spot", cascade = CascadeType.ALL )
	@JsonIgnoreProperties(value = "spot")
	List<Reservation> reservationList = new ArrayList<>();

	public Spot(SpotType spotType, int pricePerHour, boolean ocuupied) {
		super();
		this.spotType = spotType;
		this.pricePerHour = pricePerHour;
		this.occupied = ocuupied;
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

	@Override
	public String toString() {
		return "Spot [id=" + id + ", spotType=" + spotType + ", pricePerHour=" + pricePerHour + ", occupied=" + occupied
				+ "]";
	}
	
	
	
	
}
