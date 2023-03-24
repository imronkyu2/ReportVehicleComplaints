package com.example.reportvehiclecomplaints.modul.dialogfragment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reportvehiclecomplaints.databinding.AdapterVehicleListBinding;
import com.example.reportvehiclecomplaints.model.VehicleModel;

import java.util.List;


public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {
    private final List<VehicleModel> itemsList;
    private final VehicleListAdapterViewContract viewContract;

    public VehicleListAdapter(List<VehicleModel> itemsList, VehicleListAdapterViewContract viewContract) {
        this.itemsList = itemsList;
        this.viewContract = viewContract;
    }

    @NonNull
    @Override
    public VehicleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehicleListAdapter.ViewHolder(AdapterVehicleListBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleListAdapter.ViewHolder holder, int position) {
        final VehicleModel data = itemsList.get(position);
        holder.itemViewBinding.vehicleTypeTV.setText(data.getType());
        holder.itemViewBinding.vehicleTypeTV.setOnClickListener(view -> viewContract.doSelectVehicleType(data));
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterVehicleListBinding itemViewBinding;

        public ViewHolder(@NonNull AdapterVehicleListBinding itemView) {
            super(itemView.getRoot());
            this.itemViewBinding = itemView;
        }
    }
}