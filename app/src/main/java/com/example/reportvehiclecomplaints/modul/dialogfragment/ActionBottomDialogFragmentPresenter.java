package com.example.reportvehiclecomplaints.modul.dialogfragment;

import com.example.reportvehiclecomplaints.core.repo.VehicleComplaintRepo;
import com.example.reportvehiclecomplaints.model.VehicleModel;
import com.example.reportvehiclecomplaints.modul.dialogfragment.contract.ActionBottomDialogFragmentPresenterContract;
import com.example.reportvehiclecomplaints.modul.dialogfragment.contract.ActionBottomDialogFragmentViewContract;
import com.example.reportvehiclecomplaints.util.mvp.LoadCallback;
import com.example.reportvehiclecomplaints.util.mvp.PostCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionBottomDialogFragmentPresenter implements ActionBottomDialogFragmentPresenterContract {
    private final VehicleComplaintRepo repo;
    private final ActionBottomDialogFragmentViewContract viewContract;

    public ActionBottomDialogFragmentPresenter(VehicleComplaintRepo repo,
                                               ActionBottomDialogFragmentViewContract homeView) {
        this.repo = repo;
        this.viewContract = homeView;
        viewContract.setPresenter(this);
    }

    @Override
    public void doGetListVehicle() {
        viewContract.showContentLoading();
        repo.doGetListVehicle(new LoadCallback<VehicleModel>() {
            @Override
            public void onLoadedData(Object var1) {
                viewContract.doGetListVehicleSuccessfully((List<VehicleModel>) var1);
                viewContract.hideContentLoading();
            }

            @Override
            public void onDataNotAvailable() {
                viewContract.doGetListVehicleSuccessfully(new ArrayList<>());
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
                        viewContract.doGetListVehicleFailed(throwable.toString());
                    }
                }
                viewContract.hideContentLoading();
            }
        });
    }

    @Override
    public void doAddReport(String vehicleId, String note, String userId, File imageFile) {
        viewContract.showContentLoading();
        repo.doRegister(vehicleId, note, userId, imageFile, new PostCallback() {
            @Override
            public void onEntityPosted(Object entity) {
                viewContract.doAddReportSuccessfully();
                viewContract.hideContentLoading();

            }

            @Override
            public void onErrorRequest(Throwable throwable) {
                String string = throwable.getMessage();
                viewContract.doAddReportFailed(string);
                viewContract.hideContentLoading();

            }
        });
    }
}
