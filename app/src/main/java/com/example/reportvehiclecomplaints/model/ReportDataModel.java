package com.example.reportvehiclecomplaints.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDataModel {

    @JsonProperty("note")
    private String note;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("vehicleName")
    private String vehicleName;

    @JsonProperty("reportId")
    private String reportId;

    @JsonProperty("vehicleLicenseNumber")
    private String vehicleLicenseNumber;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("reportStatus")
    private String reportStatus;

    @JsonProperty("vehicleId")
    private String vehicleId;

    public ReportDataModel() {
    }

    public String getNote() {
        return note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getReportId() {
        return reportId;
    }

    public String getVehicleLicenseNumber() {
        return vehicleLicenseNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getPhoto() {
        return photo;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}