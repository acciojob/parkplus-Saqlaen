package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
	
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    
    @Override
    public ParkingLot addParkingLot(String name, String address) {
    	// creating the parking lot 
    	ParkingLot parkingLot = new ParkingLot();
    	
    	parkingLot.setName(name);
    	parkingLot.setAddress(address);
    	
    	parkingLotRepository1.save( parkingLot );
    	System.out.println( parkingLot.toString() );
    	
    	return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
    	Spot parkingSpot = new Spot();
    	
    	if( (int)numberOfWheels == 2 ) {
    		parkingSpot.setSpotType( SpotType.TWO_WHEELER );
    	}
    	else if( (int)numberOfWheels == 4 ) {
    		parkingSpot.setSpotType( SpotType.FOUR_WHEELER );
    	}
    	else {
    		if( (int)numberOfWheels > 4 ) {
    			parkingSpot.setSpotType( SpotType.OTHERS );
    		}
    	}
    	parkingSpot.setPricePerHour(pricePerHour);
    	parkingSpot.setOccupied(false);
    	
    	ParkingLot lot = parkingLotRepository1.findById(parkingLotId).get();
    	List<Spot> spotsList = lot.getSpotList();
    	spotsList.add( parkingSpot );
    	
    	lot.setSpotList(spotsList);
    	
    	parkingLotRepository1.save( lot );
    	System.out.println( parkingSpot.toString() );
    	
    	return parkingSpot;
    	
    	
    }

    @Override
    public void deleteSpot(int spotId) {
    	spotRepository1.deleteById( spotId );
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
    	
    	ParkingLot myLot = parkingLotRepository1.findById( parkingLotId ).get();
    	
    	Spot spot  = null;
    	List<Spot> spotList = myLot.getSpotList();
    	
    	for( Spot myspot : spotList ) {
    		if( myspot.getId() == spotId ) {
    			spot = myspot;
    			break;
    		}
    	}
    	spot.setPricePerHour(pricePerHour);
    	myLot.setSpotList(spotList);
    	
    	spotRepository1.save( spot );
    	
    	parkingLotRepository1.save( myLot );
    	System.out.println( myLot.toString() );
    	return spot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
    	parkingLotRepository1.deleteById( parkingLotId );
    }
}
