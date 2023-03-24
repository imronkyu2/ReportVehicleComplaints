package com.example.reportvehiclecomplaints.modul.home;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.reportvehiclecomplaints.databinding.ActivityHomeReportDataBinding;
import com.example.reportvehiclecomplaints.model.ReportDataModel;
import com.example.reportvehiclecomplaints.modul.dialogfragment.ActionBottomDialogFragment;
import com.example.reportvehiclecomplaints.modul.dialogfragment.DialogListener;
import com.example.reportvehiclecomplaints.modul.home.adapter.ComplaintReportHistoryAdapter;
import com.example.reportvehiclecomplaints.modul.home.contract.HomeReportDataPresenterContract;
import com.example.reportvehiclecomplaints.modul.home.contract.HomeReportDataViewContract;
import com.example.reportvehiclecomplaints.util.Injection;

import java.util.ArrayList;
import java.util.List;

public class HomeReportDataActivity extends AppCompatActivity implements HomeReportDataViewContract,
        DialogListener {
    private HomeReportDataPresenterContract presenterContract;
    private ActivityHomeReportDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeReportDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new HomeReportDataPresenter(Injection.provideRepository(getApplicationContext()),
                HomeReportDataActivity.this);

        binding.buttonNewReport.setOnClickListener(v -> actionBottomDialogFragment());
    }

    private void actionBottomDialogFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ActionBottomDialogFragment fragment = ActionBottomDialogFragment.newInstance();
        fragment.show(fm, "ActionBottomDialogFragment");
    }


    @Override
    public void doGetListAllReportDataFailed(String toString) {
        Log.e(TAG, "doGetListAllReportDataFailed: " + toString);
        binding.dataNotFound.setVisibility(View.VISIBLE);
        binding.complaintRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void doGetListAllReportDataSuccessfully(List<ReportDataModel> reportDataModelList) {
        binding.complaintRecyclerView.setHasFixedSize(true);
        binding.complaintRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ComplaintReportHistoryAdapter brandAdapter;
        if (reportDataModelList.size() > 0) {
            brandAdapter = new ComplaintReportHistoryAdapter(getApplicationContext(), reportDataModelList);
            binding.dataNotFound.setVisibility(View.GONE);
            binding.complaintRecyclerView.setVisibility(View.VISIBLE);
        } else {
            brandAdapter = new ComplaintReportHistoryAdapter(getApplicationContext(), new ArrayList<>());
            binding.dataNotFound.setVisibility(View.VISIBLE);
            binding.complaintRecyclerView.setVisibility(View.GONE);
        }
        binding.complaintRecyclerView.setAdapter(brandAdapter);
        binding.complaintRecyclerView.scheduleLayoutAnimation();

    }


    @Override
    public void setPresenter(HomeReportDataPresenterContract presenter) {
        this.presenterContract = presenter;
        presenterContract.doGetListAllReportData("");

    }

    @Override
    public void showConnectionError() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContentLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showConnectionFailed() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFinishDialog(Boolean reportSuccess) {
        if (reportSuccess) {
            presenterContract.doGetListAllReportData("");
        }
    }
}