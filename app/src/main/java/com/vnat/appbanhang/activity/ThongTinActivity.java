package com.vnat.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vnat.appbanhang.R;

public class ThongTinActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar toolbarThongTin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbarThongTin = findViewById(R.id.toolbarThongTin);
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(toolbarThongTin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng phongtrocuatui = new LatLng(10.819813, 106.686532);
        mMap.addMarker(new MarkerOptions().position(phongtrocuatui)
                .title("Phòng trọ của tui")
                .snippet("99/9 Lê Lợi, p.4 quận Gò Vấp, TPHCM")
                .icon(BitmapDescriptorFactory.defaultMarker()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(phongtrocuatui));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(phongtrocuatui).zoom(90).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
