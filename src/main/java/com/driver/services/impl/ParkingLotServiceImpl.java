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
    	ParkingLot lot = parkingLotRepository1.findById(parkingLotId).get();
    	
    	Spot parkingSpot = new Spot();
    	if( numberOfWheels <= 2 ) {
    		parkingSpot.setSpotType( SpotType.TWO_WHEELER );
    	}
    	else if( numberOfWheels > 2 && numberOfWheels <= 4 ) {
    		parkingSpot.setSpotType( SpotType.FOUR_WHEELER );
    	}
    	else {
    		if( numberOfWheels > 4 ) {
    			parkingSpot.setSpotType( SpotType.OTHERS );
    		}
    	}
    	parkingSpot.setPricePerHour(pricePerHour);
    	parkingSpot.setOccupied(false);
    	parkingSpot.setParkingLot(lot);
    	
//    	spotRepository1.save( parkingSpot );
    	
    	List<Spot> spotsList = lot.getSpotList();
    	spotsList.add( parkingSpot );
    	lot.setSpotList(spotsList);
    	lot.setId(parkingLotId);
    	
    	parkingLotRepository1.save( lot );
    	
    	System.out.println( parkingSpot.toString() );
    	
    	return spotsList.get(spotsList.size() - 1 );
    	
    	
    }

    @Override
    public void deleteSpot(int spotId) {
    	spotRepository1.deleteById( spotId );
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
    	
    	ParkingLot myLot = parkingLotRepository1.findById( parkingLotId ).get();

    	Spot spot  = spotRepository1.findById( spotId ).get();
    	spot.setPricePerHour(pricePerHour);
    	spot.setParkingLot(myLot);
    	spot.setId(spotId);
    	
    	List<Spot> spotList = myLot.getSpotList();
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
