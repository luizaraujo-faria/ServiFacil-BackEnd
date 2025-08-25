package com.servifacil.SF_BackEnd.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tbAddresses")
public class AddressModel {

    @Id
    @Column(name = "Address_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressID;

    @Column(name = "Zip_Code")
    @NotBlank
    private String zipCode;

    @Column(name = "Street")
    @NotBlank
    private String street;

    @Column(name = "House_Number")
    @NotBlank
    private String houseNumber;

    @Column(name = "Complement")
    private String complement;

    @Column(name = "Neighborhood_ID")
    private int neighborhoodID;

    @Column(name = "City_ID")
    private int cityID;

    @Column(name = "State_ID")
    private int stateID;

    // GETTERS e SETTERS

    public int getAddressID(){ return addressID; }

    public String getZipCode(){ return zipCode; }
    public void setZipCode(String zipCode){ this.zipCode = zipCode; }

    public String getStreet(){ return street; }
    public void setStreet(String street){ this.street = street; }

    public String getHouseNumber(){ return houseNumber; }
    public void setHouseNumber(String houseNumber){ this.houseNumber = houseNumber; }

    public String getComplement(){ return complement; }
    public void setComplement(String complement){ this.complement = complement; }

    public int getStateID(){ return stateID; }
    public void setStateID(int stateID){ this.stateID = stateID; }

    public int getCityID(){ return cityID; }
    public void setCityID(int cityID){ this.cityID = cityID; }

    public int getNeighborhoodID(){ return neighborhoodID; }
    public void setNeighborhoodID(int neighborhoodID){ this.neighborhoodID = neighborhoodID; }
}
