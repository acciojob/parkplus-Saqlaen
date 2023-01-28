package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
	
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
    	ParkingLot parkinglot = parkingLotRepository3.findById(parkingLotId).get();
    	User user = userRepository3.findById( userId ).get();
    	
    	List<Spot> listOfSpotsInParking = parkinglot.getSpotList();
    	List<Spot> availableSpots =  listOfSpotsInParking.stream().filter( spot -> !spot.isOcuupied() ).collect( Collectors.toList() );
    	
    	if( parkinglot == null || user == null || availableSpots.size() == 0 ) {
    		throw new Exception("Cannot make reservation");
    	}
    	
    	List<Spot> filteredSpots = null;
    	// select spot which can accomodate the numberofwheels
    	if( numberOfWheels == 2 ) {
    		for( Spot spot : availableSpots ) {
    			if( spot.getSpotType() == SpotType.TWO_WHEELER || 
    				spot.getSpotType() == SpotType.FOUR_WHEELER || 
    				spot.getSpotType() == SpotType.OTHERS ) {
    				filteredSpots.add( spot );
    			}
    		}
    	}else if( numberOfWheels == 4 ) {
    		for( Spot spot : availableSpots ) {
    			if( spot.getSpotType() == SpotType.FOUR_WHEELER || 
    				spot.getSpotType() == SpotType.OTHERS ) {
    				filteredSpots.add( spot );
    			}
    		}
    	}
    	else {
    		for( Spot spot : availableSpots ) {
    			if( spot.getSpotType() == SpotType.OTHERS ) {
    				filteredSpots.add( spot );
    			}
    		}
    	}
    	
    	// finding the minimum spot price 
    	int MinPricePerHour = Integer.MAX_VALUE;
    	int MinTotalPrice = Integer.MAX_VALUE;
    	Spot MinPriceSpot = null;
    	for( Spot spot : availableSpots ) {
    		if( spot.getPricePerHour() < MinPricePerHour ) {
    			MinPricePerHour = spot.getPricePerHour();
    			MinPriceSpot = spot;
    			
    			int TotalPrice = MinPricePerHour * timeInHours;
    			if( TotalPrice < MinTotalPrice ) {
    				MinTotalPrice = TotalPrice;
    			}
    			
    		}
    	}
    	
    	
    	Reservation reservation = new Reservation();
    	reservation.setNoOfHours( timeInHours );
    	reservation.setUser(user);
    	reservation.setSpot(MinPriceSpot);
    	reservation.setBillAmount(MinTotalPrice);
    	
    	List<Reservation> reservationList = MinPriceSpot.getReservationList();
    	reservationList.add( reservation );
    	MinPriceSpot.setReservationList( reservationList );
    	
    	spotRepository3.save( MinPriceSpot );
    	
    	List<Reservation> listReservation = user.getReservationList();
    	listReservation.add( reservation );
    	user.setReservationList( listReservation );
    	
    	userRepository3.save( user );
    	
    	System.out.println( "reservation" + reservation.toString() );
    	System.out.println( "spot" + parkinglot.toString() );
    	
    	return reservation;
    	
    }
}
