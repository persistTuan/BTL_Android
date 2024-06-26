package com.tlu.edu.vn.ht63.btl_nhom10.Model.Api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DistanceCalculator {
    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String API_KEY = "AIzaSyBhK8KmSB_ByD0kN3Aas23jDzGIGXRrFrc";
    private static final String STORE_ADDRESS = "175 P. Tây Sơn, Trung Liệt, Đống Đa, Hà Nội 116705, Việt Nam"; // Địa chỉ cửa hàng của bạn

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static DistanceMatrixApi distanceMatrixApi = retrofit.create(DistanceMatrixApi.class);

    public interface DistanceCallback {
        void onDistanceCalculated(String distance, String duration);
        void onError(String error);
    }

    public static void calculateDistance(String destinationAddress, final DistanceCallback callback) {
        Call<DistanceResponse> call = distanceMatrixApi.getDistance(STORE_ADDRESS, destinationAddress, API_KEY, "driving");
        call.enqueue(new Callback<DistanceResponse>() {
            @Override
            public void onResponse(@NonNull Call<DistanceResponse> call, @NonNull Response<DistanceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Gson gson = new Gson();
                    String jsonBody = gson.toJson(response.body());
                    Log.i("body", jsonBody);
                    DistanceResponse distanceResponse = response.body();
                    if ("OK".equals(distanceResponse.getStatus()) && distanceResponse.getRows() != null && !distanceResponse.getRows().isEmpty()) {
                        Row row = distanceResponse.getRows().get(0);
                        if (row.getElements() != null && !row.getElements().isEmpty()) {
                            Element element = row.getElements().get(0);
                            if ("OK".equals(element.getStatus()) && element.getDistance() != null && element.getDuration() != null) {
                                String distance = element.getDistance().getText();
                                String duration = element.getDuration().getText();
                                callback.onDistanceCalculated(distance, duration);
                                return;
                            }
                        }
                    }
                    callback.onError("Không thể tính khoảng cách");
                } else {
                    callback.onError("Lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DistanceResponse> call, Throwable t) {
                callback.onError("Lỗi khi gửi yêu cầu: " + t.getMessage());
            }
        });
    }
}