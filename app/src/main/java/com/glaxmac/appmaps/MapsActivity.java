package com.glaxmac.appmaps;

import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng autonoma = new LatLng(-12.195483,-76.9719602);
        LatLng libreria = new LatLng(-12.1950265,-76.9716449);
        LatLng jugos = new LatLng(-12.1963635,-76.9721322);

        LatLng makro = new LatLng(-12.193232,-76.970749);
        LatLng parque = new LatLng(-12.191522 ,-76.968874);

        mMap.addMarker(new MarkerOptions().position(autonoma).title("Universidad Autónoma del Perú"));
        mMap.addMarker(new MarkerOptions().position(libreria).title("Librería"));
        mMap.addMarker(new MarkerOptions().position(jugos).title("Jugos"));
        mMap.addMarker(new MarkerOptions().position(parque).title("Parque"));
        mMap.addMarker(new MarkerOptions().position(makro).title("Makro"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(autonoma,18));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String markerTitle = marker.getTitle();
                String url = "";

                if(markerTitle.equals("Universidad Autónoma del Perú")){
                    url = "https://e.rpp-noticias.io/normal/2017/09/18/284028_484580.jpg";
                }else if(markerTitle.equals("Makro")){
                    url = "https://www.peru-retail.com/wp-content/uploads/Makro-iniciar%C3%A1-operaciones-en-Villa-El-Salvador.jpg";
                }else if(markerTitle.equals("Parque")){
                    url = "https://cdne.diariocorreo.pe/thumbs/uploads/articles/images/fin-de-ano-entradas-a-parques-zonales-seran-59054-jpg_976x0.jpg";
                }else{
                    url = "NODATA";
                }

                if(!url.equals("NODATA")){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MapsActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.modal_image, null);

                    TextView textImage = mView.findViewById(R.id.textImage);
                    ImageView imageInstitucion = mView.findViewById(R.id.imageInstitucion);

                    textImage.setText(markerTitle);

                    Picasso.with(MapsActivity.this).load(url).placeholder(R.drawable.ic_file_download_black_24dp)
                            .error(R.mipmap.ic_launcher)
                            .into(imageInstitucion,new Callback(){
                                @Override
                                public void onSuccess() {

                                }
                                @Override
                                public void onError() {

                                }
                            });

                    mBuilder.setView(mView);
                    final AlertDialog alertdialog = mBuilder.create();
                    alertdialog.show();

                }else {
                    Toast.makeText(MapsActivity.this, "NO HAY IMAGENES DE ESTA UBICACIÓN", Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });
    }
}