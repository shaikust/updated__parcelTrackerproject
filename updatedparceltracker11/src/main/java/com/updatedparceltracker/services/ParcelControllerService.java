package com.updatedparceltracker.services;

import com.updatedparceltracker.exception.ResourceNotFoundException;
import com.updatedparceltracker.model.Parcel;
import com.updatedparceltracker.repository.ParcelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ParcelControllerService {
  final Logger logger= LoggerFactory.getLogger(ParcelControllerService.class);
@Autowired
  private  ParcelRepository parcelRepository;
public ResponseEntity<List<Parcel>> getAllParcel() {
  try {
    return  ResponseEntity.ok(parcelRepository.findAll());

  }catch (Exception e){
    return (ResponseEntity<List<Parcel>>) ResponseEntity.badRequest();
  }
}
  public ResponseEntity<String> addParcel(Parcel parcel) {
    try {
      parcelRepository.save(parcel);
      logger.info("new parcel added");
      return new ResponseEntity<String>("["+parcel.getTrackingId()+"]"+"parcel added successfully",HttpStatus.OK);
    }catch (Exception e){

      return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

  }
  public  ResponseEntity<Parcel> getParcel(Integer trackingId) {
    logger.info("get parcel with given id");

      return new ResponseEntity<Parcel>(this.parcelRepository.findById(trackingId).orElseThrow(()->new ResourceNotFoundException("parcel","id",trackingId)),HttpStatus.FOUND);

  }
public ResponseEntity<String> updateParcel(Integer trackingId, Parcel updateParcel) {
  try {
    Parcel parcel=parcelRepository.findById(trackingId).orElseThrow(()->new UsernameNotFoundException("Parcel "+updateParcel.toString()+"with trackingId "+trackingId+"not found"));
    parcel.setCurrentLocation(updateParcel.getCurrentLocation());
    parcel.setDeliveryStatus(updateParcel.getDeliveryStatus());
    parcelRepository.save(parcel);
    logger.info("parcel updated");
    return new ResponseEntity<String>("updated successfully", HttpStatus.OK);
  }catch (Exception e){
    return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

  }
}
  public ResponseEntity<String> deleteParcel(Integer trackingId) {
    try {
      Parcel parcel=parcelRepository.findById(trackingId).orElseThrow(()->new ResourceNotFoundException("parcel","trackingId",trackingId));
      parcelRepository.delete(parcel);
      logger.info("parcel deleted with given id");
    return new ResponseEntity<>("deleted successfully",HttpStatus.OK);

    }catch (Exception e){
      return  new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

    }
  }


}
