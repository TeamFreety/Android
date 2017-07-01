package com.sopt.freety.freety.view.recruit;

import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.MapNetworkService;
import com.sopt.freety.freety.util.permission.PermissionUtil;
import com.sopt.freety.freety.view.recruit.data.PlacesResults;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPopupActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    private static final String CAMERA_POSITION = "camera_position";
    private static final String LOCATION = "location";
    private static final int DEFAULT_ZOOM = 15;
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
    private static final String TAG = "MapPopupActivity";
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    private boolean locationPermissionGranted;
    private LocationRequest locationRequest;
    private Location currentLocation;
    private CameraPosition cameraPosition;
    private MapNetworkService mapNetworkService;

    private ArrayAdapter listAdapter;
    private List<PlacesResults.PlaceResult> placeResults;
    @BindView(R.id.recruit_popup_list)
    ListView searchListView;

    @BindView(R.id.recruit_popup_edit)
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentLocation = savedInstanceState.getParcelable(LOCATION);
            cameraPosition = savedInstanceState.getParcelable(CAMERA_POSITION);
        }
        setContentView(R.layout.activity_map_popup);
        ButterKnife.bind(this);
        mapNetworkService = AppController.getInstance().getMapNetworkService();
        buildGoogleApiClient();
        googleApiClient.connect();
        List<String> initResultList = new ArrayList<>();
        placeResults = new ArrayList<>();
        initResultList.add("검색 결과 아직 없음.");
        listAdapter = new ArrayAdapter(getApplicationContext(), R.layout.recruit_popup_search_result_list, initResultList);
        searchListView.setAdapter(listAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlacesResults.PlaceResult selectedPlace = placeResults.get(position);
                moveCameraToLatLng(selectedPlace.getLat(), selectedPlace.getLng(), selectedPlace.getName());
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == PermissionUtil.REQUEST_LOCATION) {
            if (PermissionUtil.verifyPermission(grantResults)) {
                locationPermissionGranted = true;
            } else {
            }
        }
        updateLocationUI();
    }

    protected synchronized void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();
    }

    @SuppressWarnings("MissingPermission")
    private void getDeviceLocation() {
        if (PermissionUtil.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationPermissionGranted = true;
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            PermissionUtil.requestFineLocationPermissions(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (cameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
            googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("현재 위치"));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void moveCameraToLatLng(double lat, double lng, String locationName) {
        LatLng currentLatLng = new LatLng(lat, lng);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
        googleMap.addMarker(new MarkerOptions().position(currentLatLng).title(locationName).snippet("이 곳을 확인하세요."));
    }

    @OnClick(R.id.recruit_popup_exit)
    void onExit() {
        finish();
    }

    @OnClick(R.id.recruit_popup_search)
    void onSearch() {
        final String searchText = editText.getText().toString();
        Log.i(TAG, "onSearch: searchText is : " + searchText);
        final Call<PlacesResults> requestPlacesResults = mapNetworkService.getLocationResults(searchText);
        requestPlacesResults.enqueue(new Callback<PlacesResults>() {
            @Override
            public void onResponse(Call<PlacesResults> call, Response<PlacesResults> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.raw().toString());
                    List<String> resultList = new ArrayList<>();
                    placeResults = response.body().getResults();
                    for (int index = 0; index < placeResults.size(); index++) {
                        PlacesResults.PlaceResult result = placeResults.get(index);
                        resultList.add(result.getName() + " / " + result.getFormattedAddress());
                        if (index >= 10) {
                            break;
                        }
                    }
                    if (resultList.size() == 0) {
                        resultList.add("검색 결과 없음.");
                    }
                    Log.i(TAG, "onResponse: " + resultList.toString());
                    listAdapter.clear();
                    listAdapter.addAll(resultList);
                }
            }

            @Override
            public void onFailure(Call<PlacesResults> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getDeviceLocation();
        MapFragment mapFragment =
                (MapFragment) getFragmentManager().findFragmentById(R.id.recruit_popup_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (googleMap != null) {
            outState.putParcelable(CAMERA_POSITION, googleMap.getCameraPosition());
            outState.putParcelable(LOCATION, currentLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    protected void onResume() {
        if (googleApiClient.isConnected()) {
            getDeviceLocation();
        }
        super.onResume();
    }

    @SuppressWarnings("MissingPermission")
    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }

        if (locationPermissionGranted) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}