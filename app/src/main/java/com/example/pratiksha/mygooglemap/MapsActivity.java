package com.example.pratiksha.mygooglemap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    //Location Client
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //POP UP box for request the permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            getMyLocation();
            Toast.makeText(this, "Permission is allowed", Toast.LENGTH_SHORT).show();
        }

        // Add a marker in Sydney and move the camera
        LatLng vimanNagar = new LatLng(18.566526, 73.912239);
        LatLng yerawada = new LatLng(18.5529, 73.8796);
        LatLng kalyaniNagar = new LatLng(18.5463, 73.9033);
        LatLng baner = new LatLng(18.5590, 73.7868);
        //mMap.addMarker(new MarkerOptions().position(vimanNagar).title("Marker in Yerawada"));
        MarkerOptions markerOptionsVnagar = new MarkerOptions().position(vimanNagar).title(" Viman Nagar ");
        MarkerOptions markerOptionsyerawada = new MarkerOptions().position(yerawada).title(" Yerawada");
        MarkerOptions markerOptionsKnagar = new MarkerOptions().position(kalyaniNagar).title(" Kalyani Nagar");
        MarkerOptions markerOptionsbaner = new MarkerOptions().position(vimanNagar).title(" Baner ");
        mMap.addMarker(markerOptionsVnagar);
        mMap.addMarker(markerOptionsyerawada);
        mMap.addMarker(markerOptionsKnagar);
        mMap.addMarker(markerOptionsbaner);

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vimanNagar, 12.01f));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vimanNagar, 12.0f));
        CircleOptions circleOptions = new CircleOptions().center(vimanNagar).radius(1000).strokeColor(Color.BLACK).fillColor(Color.BLUE);

        mMap.addCircle(circleOptions);

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(vimanNagar);
        polylineOptions.add(yerawada);
        polylineOptions.add(kalyaniNagar);
        polylineOptions.add(baner);
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);

        mMap.addPolyline(polylineOptions);

        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(vimanNagar);
        polygonOptions.add(yerawada);
        polygonOptions.add(kalyaniNagar);
        polygonOptions.add(baner);
        polygonOptions.add(vimanNagar);
        polygonOptions.fillColor(Color.CYAN);

        mMap.addPolygon(polygonOptions);

    }


    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(myLocation).title("My Location");

                    mMap.addMarker(markerOptions);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            } else {
                Toast.makeText(this, "Permission is mandatory", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            }
        }
    }
}