package com.example.reportvehiclecomplaints.core.repo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.reportvehiclecomplaints.core.networking.ApiService;
import com.example.reportvehiclecomplaints.core.networking.RetrofitHttpsCall;
import com.example.reportvehiclecomplaints.model.BaseDataModel;
import com.example.reportvehiclecomplaints.model.ReportDataModel;
import com.example.reportvehiclecomplaints.model.VehicleModel;
import com.example.reportvehiclecomplaints.util.mvp.LoadCallback;
import com.example.reportvehiclecomplaints.util.mvp.PostCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleComplaintRepoImpl implements VehicleComplaintRepo {
    private final ApiService apiService;

    public VehicleComplaintRepoImpl(Context mContext) {
        this.apiService = RetrofitHttpsCall.getInstance(mContext).create(ApiService.class);
    }

    @Override
    public void doGetListAllReportData(@NonNull HashMap map, LoadCallback<ReportDataModel> callback) {
        Call<List<ReportDataModel>> responseCall = apiService.doGetListAllReportData(map);
        responseCall.enqueue(new Callback<List<ReportDataModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ReportDataModel>> call,
                                   @NonNull Response<List<ReportDataModel>> response) {
                if (response.isSuccessful()) {
                    try {
                        List<ReportDataModel> wrapper = response.body();
                        if (wrapper != null) {
                            callback.onLoadedData(wrapper);
                        } else {
                            callback.onErrorRequest(new Exception(response.message()));
                        }
                    } catch (RuntimeException ex) {
                        Log.e("RuntimeException", ex.getMessage());
                        callback.onErrorRequest(new Exception(ex.getMessage()));
                    }
                } else {
                    callback.onErrorRequest(new Exception(response.message()));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ReportDataModel>> call, @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void doGetListVehicle(LoadCallback<VehicleModel> callback) {
        Call<List<VehicleModel>> responseCall = apiService.doGetListVehicle();
        responseCall.enqueue(new Callback<List<VehicleModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<VehicleModel>> call,
                                   @NonNull Response<List<VehicleModel>> response) {
                if (response.isSuccessful()) {
                    try {
                        List<VehicleModel> wrapper = response.body();
                        if (wrapper != null) {
                            callback.onLoadedData(wrapper);
                        } else {
                            callback.onErrorRequest(new Exception(response.message()));
                        }
                    } catch (RuntimeException ex) {
                        Log.e("RuntimeException", ex.getMessage());
                        callback.onErrorRequest(new Exception(ex.getMessage()));
                    }
                } else {
                    callback.onErrorRequest(new Exception(response.message()));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<VehicleModel>> call,
                                  @NonNull Throwable t) {
                assert callback != null;
                callback.onErrorRequest(t);
            }
        });
    }

    @Override
    public void doRegister(String vehicleId_data, String note_data, String userId_data, File imageFile, PostCallback postCallback) {
        RequestBody vehicleId = RequestBody.create(MediaType.parse("text/plain"), vehicleId_data);
        RequestBody note = RequestBody.create(MediaType.parse("text/plain"), note_data);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userId_data);
        RequestBody photo = RequestBody.create(MediaType.parse("image/*"),
                imageFile);

        Call<BaseDataModel> responseCall = apiService.doAddReport(vehicleId, note, userId, photo);
        responseCall.enqueue(new Callback<BaseDataModel>() {
            @Override
            public void onResponse(@NonNull Call<BaseDataModel> call, @NonNull Response<BaseDataModel> response) {
                if (response.isSuccessful()) {
                    BaseDataModel wrapper = response.body();
                    assert wrapper != null;
                    if (wrapper.getStatus()) {
                        postCallback.onEntityPosted(wrapper);
                    } else {
                        postCallback.onErrorRequest(new Exception(wrapper.getMessage()));
                    }
                } else {
                    postCallback.onErrorRequest(new Exception(response.message()));
                }

            }

            @Override
            public void onFailure(@NonNull Call<BaseDataModel> call, @NonNull Throwable t) {
                assert postCallback != null;
                postCallback.onErrorRequest(t);
            }
        });
    }
}
