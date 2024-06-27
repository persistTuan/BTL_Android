package com.tlu.edu.vn.ht63.btl_nhom10.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.Api.DistanceCalculator;
import com.tlu.edu.vn.ht63.btl_nhom10.Model.User;
import com.tlu.edu.vn.ht63.btl_nhom10.R;
import com.tlu.edu.vn.ht63.btl_nhom10.Reponsitory.UserReponsitory;

import java.util.Arrays;

public class SearchPlaceActivity extends AppCompatActivity {
    private AutocompleteSupportFragment autocompleteFragment;
    private UserReponsitory userReponsitory;

    private User userCurrent;
    Button btnAccept;

    TextView txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        userReponsitory = new UserReponsitory(this);
        userCurrent = userReponsitory.getUserCurrent();
        btnAccept = findViewById(R.id.btnAccept);
        txtAddress = findViewById(R.id.txt_address);
        initData();

        actionView();

    }

    private void actionView() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("address", userCurrent.getAddress());
                userReponsitory.updateUser(userCurrent, new UserReponsitory.OnUserListener() {
                    @Override
                    public void onChangeData(boolean success) {

                    }
                });
                Toast.makeText(SearchPlaceActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("address", userCurrent.getAddress());
                intent.putExtra("distance", userCurrent.getDistance());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        txtAddress.setText(userCurrent.getAddress());
    }

    void initData(){
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), getString(R.string.key2_api_ggmap));
        }

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG, Place.Field.TYPES));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                DistanceCalculator.calculateDistance(place.getAddress(), new DistanceCalculator.DistanceCallback() {
                    @Override
                    public void onDistanceCalculated(String distance, String duration) {
//                        Toast.makeText(SearchPlaceActivity.this, distance, Toast.LENGTH_SHORT).show();
                        txtAddress.setText(place.getName() + " - " + place.getAddress());
                        String[] numberDistance = distance.split(" ");
                        userCurrent.setAddress(place.getName() + " - " + place.getAddress());
                        userCurrent.setDistance(Float.parseFloat(numberDistance[0]));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(SearchPlaceActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @Override
            public void onError(@NonNull Status status) {
                Log.e("onSearchPlace", status + "");

                Toast.makeText(SearchPlaceActivity.this, status + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}