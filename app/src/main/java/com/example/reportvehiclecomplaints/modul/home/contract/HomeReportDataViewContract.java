package com.example.reportvehiclecomplaints.modul.home.contract;

import com.example.reportvehiclecomplaints.model.ReportDataModel;
import com.example.reportvehiclecomplaints.util.mvp.BaseView;

import java.util.List;

public interface HomeReportDataViewContract extends BaseView<HomeReportDataPresenterContract> {
    void doGetListAllReportDataFailed(String toString);

    void doGetListAllReportDataSuccessfully(List<ReportDataModel> var1);
}
