package com.example.reportvehiclecomplaints.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.reportvehiclecomplaints.core.repo.VehicleComplaintRepo;
import com.example.reportvehiclecomplaints.core.repo.VehicleComplaintRepoImpl;

public class Injection {

    public static VehicleComplaintRepo provideRepository(@NonNull Context context) {
        // todo getSharedPreferences
        return new VehicleComplaintRepoImpl(context);
    }

}
