package com.driver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class ParkingLot {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private int id;
	
	private String name;
	private String address;
	
	@OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL )
	@JsonIgnoreProperties(value = "parkingLot")
	private List<Spot> spotList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Spot> getSpotList() {
		return spotList;
	}

	public void setSpotList(List<Spot> spotList) {
		this.spotList = spotList;
	}

	public ParkingLot(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

	public ParkingLot() {
		super();
	}

	@Override
	public String toString() {
		return "ParkingLot [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	
	

}
