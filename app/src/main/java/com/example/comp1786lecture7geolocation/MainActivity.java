package com.example.comp1786lecture7geolocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private TextView txtLocation;

    private FusedLocationProviderClient locationClient;

    private final int REQUEST_PERMISSION_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLocation = findViewById(R.id.txtLocation);

        locationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, REQUEST_PERMISSION_FINE_LOCATION
            );
        }
        else {
            showLocation();
        }
    }

    @SuppressLint({"MissingSuperCall", "SetTextI18n"})
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        switch (requestCode) {
            case REQUEST_PERMISSION_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(MainActivity.this, "Permission Granted!",
                            Toast.LENGTH_SHORT
                    ).show();
                    showLocation();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied!",
                            Toast.LENGTH_SHORT
                    ).show();
                    txtLocation.setText("Location permission not granted");
                }
        }
    }

    @SuppressLint("MissingPermission")
    private void showLocation() {
        locationClient.getLastLocation().addOnSuccessListener(this,
                new OnSuccessListener<Location>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            txtLocation.setText("Current location is: " +
                                    "\n Latitude: " + location.getLatitude() +
                                    "\n Longitude: " + location.getLongitude()
                            );
                        }
                    }
                }
        );
    }
}