package com.example.pr13_0103;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    LocationManager _LocationManager;
    int ACCESS_FINE_LOCATION;
    int ACCESS_COARSE_LOCATION;
    double currentLatitude;
    double currentLongitude;
    String message;

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.locationText);
        _LocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
    LocationListener _LocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location == null) return;
            else {
                message = "";
                if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                    message += "\nМестоположение определено с помощью GPS:\n долгота - " + location.getLatitude() + " широта - " + location.getLongitude();
                }
                if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                    message += "\nМестоположение определено с помощью интернета:\n долгота - " + location.getLatitude() + " широта - " + location.getLongitude();
                }
                result.setText(message);
            }
        }
    };
    public Boolean GetPermissionGPS() {
        ACCESS_FINE_LOCATION = ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        ACCESS_COARSE_LOCATION = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED ||
                ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED;
    }
    public void OnGetGPS(View view){
        if(GetPermissionGPS() == false)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (GetPermissionGPS() == false) {
            return;
        } else {
            _LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, 10, _LocationListener);
            _LocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 10, _LocationListener);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        _LocationManager.removeUpdates(_LocationListener);
    }
}