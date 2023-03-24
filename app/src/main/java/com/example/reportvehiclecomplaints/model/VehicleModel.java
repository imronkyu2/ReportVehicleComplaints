package com.example.reportvehiclecomplaints.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleModel {

    @JsonProperty("licenseNumber")
    private String licenseNumber;

    @JsonProperty("vehicleId")
    private String vehicleId;

    @JsonProperty("type")
    private String type;

    public VehicleModel() {
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getType() {
        return type;
    }
}