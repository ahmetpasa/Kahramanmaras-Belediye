package com.ahmet.maras_belediye;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class konumgonder extends AppCompatActivity implements LocationListener {
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String lastLocation = "";
    String email;
    Button gonder, durdur,cikis;
    String gps = "gps";
    Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konumgonder);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        email = user.getEmail();
        gonder = findViewById(R.id.button3);
        durdur=findViewById(R.id.button2);
        durdur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(konumgonder.this,MainActivity.class));
            }
        });
        cikis=findViewById(R.id.button4);
        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(konumgonder.this,MainActivity.class));
            }
        });
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(gps);
        final String[] konumm = new String[1];
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                konumm[0] = location.toString();
                double enlem=location.getLatitude();
                double boylam=location.getLongitude();
                lastLocation+=enlem+","+boylam;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
        final String konum = konumm[0];

        durdur = findViewById(R.id.button2);
        durdur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(konumgonder.this, MainActivity.class));
            }
        });

        gonder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }


                        HashMap<String,Object> hashMap=new HashMap<>();
                        double lati = 0;
                        double longi = 0;
                        try{
                            lati = location.getLatitude();
                            longi = location.getLongitude();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                      hashMap.put("latitude", lati);
                     hashMap.put("longitude", longi);
                        hashMap.put("mail",email);
                       // hashMap.put("konum",location);
                        hashMap.put("tarih", FieldValue.serverTimestamp());
                        firestore.collection("konum").add(hashMap);
}
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        double enlem=location.getLatitude();
        double boylam=location.getLongitude();
        lastLocation+=enlem+","+boylam;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
