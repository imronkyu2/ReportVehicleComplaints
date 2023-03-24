package com.example.reportvehiclecomplaints.modul.dialogfragment.contract;

import com.example.reportvehiclecomplaints.util.mvp.BasePresenter;

import java.io.File;

public interface ActionBottomDialogFragmentPresenterContract extends BasePresenter {
    void doGetListVehicle();

    void doAddReport(String vehicleId, String note, String userId, File imageFile);
}
