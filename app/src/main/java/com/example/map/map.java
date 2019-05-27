package com.example.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.places.Places;
import com.yandex.mapkit.places.panorama.Player;

public class map extends AppCompatActivity {


    private final String MAPKIT_API_KEY = "acd81c91-1fbd-449e-8b33-fe87e80cef68";
    private final android.graphics.Point TARGET_LOCATION = new android.graphics.Point(59, 30);

    native Places getInstance();
    void	initialize(Context context){};
    public void onPanoramaDirectionChanged (@NonNull Player player){};
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        MyLocationListener.SetUpLocationListener(this);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);


        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_map);
        super.onCreate(savedInstanceState);
        mapView = (MapView)findViewById(R.id.mapview);
        //  Button b1 = (Button) findViewById(R.id.button);
        mapView.getMap().move(
                new CameraPosition(new com.yandex.mapkit.geometry.Point(), 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        // im.setText(MyLocationListener.imHere.toString());
    }

    @Override
    protected void onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }


}