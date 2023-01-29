package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    	
    	Optional<ParkingLot> parkinglot = parkingLotRepository3.findById(parkingLotId);
    	Optional<User> user = userRepository3.findById( userId );

    	if(  !parkinglot.isPresent() || parkinglot == null ) {
    		throw new Exception("null");
    	}
    	
    	if(  !user.isPresent() || user == null ) {
    		throw new Exception("null");
    	}
    	
    	ParkingLot parkingLot2 = parkinglot.get();
    	User user2 = user.get();
    	
    	
    	List<Spot> listOfSpotsInParking = parkingLot2.getSpotList();
    	List<Spot> availableSpots =  listOfSpotsInParking.stream().filter( spot -> !spot.isOccupied() ).collect( Collectors.toList() );
    	
    	if( availableSpots.size() == 0 ) {
    		throw new Exception("null");
    	}
    	
    	List<Spot> filteredSpots = new ArrayList<>();
    	// select spot which can accommodate the numberofwheels
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
    	
    	MinPriceSpot.setOccupied(true);
    	
    	Reservation reservation = new Reservation();
    	reservation.setNumberOfHours( timeInHours );
    	reservation.setUser( user2 );
    	reservation.setSpot(MinPriceSpot);
    	reservation.setBillAmount(MinTotalPrice);
    	
    	List<Reservation> reservationList = MinPriceSpot.getReservationList();
    	reservationList.add( reservation );
    	MinPriceSpot.setReservationList( reservationList );
    	
    	spotRepository3.save( MinPriceSpot );
    	
    	List<Reservation> listReservation = user2.getReservationList();
    	listReservation.add( reservation );
    	user2.setReservationList( listReservation );
    	
    	userRepository3.save( user2 );
    	
    	System.out.println( "reservation" + reservation.toString() );
    	System.out.println( "spot" + parkinglot.toString() );
    	
    	return reservation;
    	
    }
}
