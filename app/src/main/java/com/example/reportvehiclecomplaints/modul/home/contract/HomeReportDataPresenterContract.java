package com.example.reportvehiclecomplaints.modul.home.contract;

import com.example.reportvehiclecomplaints.util.mvp.BasePresenter;

public interface HomeReportDataPresenterContract extends BasePresenter {
    void doGetListAllReportData(String vehicleLicenseNumber);

}
