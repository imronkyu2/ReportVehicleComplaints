package com.example.reportvehiclecomplaints.modul.home;

import com.example.reportvehiclecomplaints.core.repo.VehicleComplaintRepo;
import com.example.reportvehiclecomplaints.model.ReportDataModel;
import com.example.reportvehiclecomplaints.modul.home.contract.HomeReportDataPresenterContract;
import com.example.reportvehiclecomplaints.modul.home.contract.HomeReportDataViewContract;
import com.example.reportvehiclecomplaints.util.mvp.LoadCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeReportDataPresenter implements HomeReportDataPresenterContract {
    private final VehicleComplaintRepo repo;
    private final HomeReportDataViewContract viewContract;

    public HomeReportDataPresenter(VehicleComplaintRepo repo, HomeReportDataViewContract homeView) {
        this.repo = repo;
        this.viewContract = homeView;
        viewContract.setPresenter(this);
    }

    @Override
    public void doGetListAllReportData(String vehicleLicenseNumber) {
        viewContract.showContentLoading();
        HashMap map = new HashMap();
        map.put("vehicleLicenseNumber", vehicleLicenseNumber);
        map.put("userId", "ewUeTxmVNi");

        repo.doGetListAllReportData(map, new LoadCallback<ReportDataModel>() {
            @Override
            public void onLoadedData(Object var1) {
                viewContract.doGetListAllReportDataSuccessfully((List<ReportDataModel>) var1);
                viewContract.hideContentLoading();
            }

            @Override
            public void onDataNotAvailable() {
                viewContract.doGetListAllReportDataSuccessfully(new ArrayList<>());
                viewContract.hideContentLoading();
            }

            @Override
            public void onErrorRequest(Throwable throwable) {
                String string = throwable.toString();
                if (throwable instanceof IOException) {
                    viewContract.showConnectionFailed();
                } else {
                    if (string.equals("500")) {
                        viewContract.showConnectionError();
                    } else {
                        viewContract.doGetListAllReportDataFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });
    }

}
