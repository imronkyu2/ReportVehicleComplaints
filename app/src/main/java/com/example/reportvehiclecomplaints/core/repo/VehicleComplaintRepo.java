package com.example.reportvehiclecomplaints.core.repo;

import androidx.annotation.NonNull;

import com.example.reportvehiclecomplaints.model.ReportDataModel;
import com.example.reportvehiclecomplaints.model.VehicleModel;
import com.example.reportvehiclecomplaints.util.mvp.LoadCallback;
import com.example.reportvehiclecomplaints.util.mvp.PostCallback;

import java.io.File;
import java.util.HashMap;

public interface VehicleComplaintRepo {

    void doGetListAllReportData(@NonNull HashMap map, LoadCallback<ReportDataModel> callback);

    void doGetListVehicle(LoadCallback<VehicleModel> callback);

    void doRegister(String vehicleId, String note, String userId, File imageFile, PostCallback postCallback);
}
