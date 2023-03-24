package com.example.reportvehiclecomplaints.core.networking;

import com.example.reportvehiclecomplaints.model.BaseDataModel;
import com.example.reportvehiclecomplaints.model.ReportDataModel;
import com.example.reportvehiclecomplaints.model.VehicleModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("android/read_all_laporan")
    Call<List<ReportDataModel>> doGetListAllReportData(@QueryMap Map<String, Object> map);

    @GET("android/list_vehicle")
    Call<List<VehicleModel>> doGetListVehicle();

    @Multipart
    @POST("android/add_laporan")
    Call<BaseDataModel> doAddReport(
            @Part("vehicleId") RequestBody vehicleId,
            @Part("note") RequestBody note,
            @Part("userId") RequestBody userId,
            @Part("photo\"; filename=\"pp.png\" ") RequestBody file);
}
