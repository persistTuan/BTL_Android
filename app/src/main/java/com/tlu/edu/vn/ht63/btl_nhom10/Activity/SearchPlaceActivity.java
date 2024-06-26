package com.tlu.edu.vn.ht63.btl_nhom10.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
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
import com.tlu.edu.vn.ht63.btl_nhom10.R;

import java.util.Arrays;

public class SearchPlaceActivity extends AppCompatActivity {
    private AutocompleteSupportFragment autocompleteFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_place);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        initData();
    }

    void initData(){
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), getString(R.string.key_api_ggmap));
        }

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG, Place.Field.TYPES));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("place", place.getAddress());
                DistanceCalculator.calculateDistance(place.getAddress(), new DistanceCalculator.DistanceCallback() {
                    @Override
                    public void onDistanceCalculated(String distance, String duration) {
                        Toast.makeText(SearchPlaceActivity.this, "Distance: " + distance, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(SearchPlaceActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
//                Log.i("Place", place.getName() + " - " + place.getAddress() );
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}