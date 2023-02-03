package com.updatedparceltracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parcel")
public class Parcel {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "trackingId")
  private Integer trackingId;
  private String senderLocation;
  private String receiverLocation;
  private String currentLocation;
  private String deliveryStatus;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "created_By",referencedColumnName = "id")
  private User user;



}
