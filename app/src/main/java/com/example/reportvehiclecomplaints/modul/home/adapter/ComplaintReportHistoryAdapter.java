package com.example.reportvehiclecomplaints.modul.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reportvehiclecomplaints.R;
import com.example.reportvehiclecomplaints.databinding.AdapterComplaintReportHistoryBinding;
import com.example.reportvehiclecomplaints.databinding.IncludeBodyComplaintReportAdapterBinding;
import com.example.reportvehiclecomplaints.databinding.IncludeFooterComplaintReportAdapterBinding;
import com.example.reportvehiclecomplaints.databinding.IncludeHeaderComplaintReportAdapterBinding;
import com.example.reportvehiclecomplaints.model.ReportDataModel;

import java.util.List;


public class ComplaintReportHistoryAdapter extends RecyclerView.Adapter<ComplaintReportHistoryAdapter.ViewHolder> {
    private final List<ReportDataModel> itemsList;
    private final Context context;

    public ComplaintReportHistoryAdapter(Context context, List<ReportDataModel> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ComplaintReportHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdapterComplaintReportHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintReportHistoryAdapter.ViewHolder holder, int position) {
        final ReportDataModel data = itemsList.get(position);
        includedHeader(data, holder.itemViewBinding.includedHeader);
        includedBody(data, holder.itemViewBinding.includedBody);
        includedFooter(data, holder.itemViewBinding.includedFooter);
    }

    private void includedHeader(ReportDataModel data, IncludeHeaderComplaintReportAdapterBinding root) {
        root.reportIdTV.setText(data.getReportId());
        root.dateTV.setText(data.getCreatedAt());
        root.statusTV.setText(data.getReportStatus());
    }

    private void includedBody(ReportDataModel data, IncludeBodyComplaintReportAdapterBinding root) {
        root.vehicleNameTV.setText(data.getVehicleName());
        root.reportByTV.setText(data.getCreatedBy());
        root.vehicleLicenseNumberTV.setText(data.getVehicleLicenseNumber());
    }

    private void includedFooter(ReportDataModel data, IncludeFooterComplaintReportAdapterBinding root) {
        root.noteValeTV.setText(data.getNote());
        Glide.with(context)
                .load(data.getPhoto())
                .placeholder(R.drawable.image_none)
                .error(R.drawable.image_none)
                .centerCrop()
                .into(root.conditionImageView);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterComplaintReportHistoryBinding itemViewBinding;

        public ViewHolder(@NonNull AdapterComplaintReportHistoryBinding itemView) {
            super(itemView.getRoot());
            this.itemViewBinding = itemView;
        }
    }
}