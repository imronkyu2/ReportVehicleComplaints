package com.example.reportvehiclecomplaints.modul.dialogfragment.contract;

import com.example.reportvehiclecomplaints.model.VehicleModel;
import com.example.reportvehiclecomplaints.util.mvp.BaseView;

import java.util.List;

public interface ActionBottomDialogFragmentViewContract extends BaseView<ActionBottomDialogFragmentPresenterContract> {
    void doGetListVehicleSuccessfully(List<VehicleModel> var1);

    void doGetListVehicleFailed(String toString);

    void doAddReportSuccessfully();

    void doAddReportFailed(String string);
}
