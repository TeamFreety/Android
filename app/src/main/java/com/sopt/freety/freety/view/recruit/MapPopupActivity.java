package com.sopt.freety.freety.view.recruit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.sopt.freety.freety.R;
import com.sopt.freety.freety.application.AppController;
import com.sopt.freety.freety.network.MapNetworkService;
import com.sopt.freety.freety.view.search.data.PlacesResults;

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
    private Location currentLocation;
    private CameraPosition cameraPosition;
    private MapNetworkService mapNetworkService;
    private boolean isPermissionGranted = false;

    @SuppressWarnings("MissingPermission")
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Log.i(TAG, "onPermissionGranted: good");
            isPermissionGranted = true;
            googleApiClient.connect();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Log.i(TAG, "onPermissionGranted: denied");
            isPermissionGranted = false;
            googleApiClient.connect();
        }
    };

    private ArrayAdapter listAdapter;
    private List<PlacesResults.PlaceResult> placeResults;
    private Intent resultIntent = new Intent();
    @BindView(R.id.recruit_popup_list)
    ListView searchListView;

    @BindView(R.id.recruit_popup_edit)
    EditText searchEditText;

    @OnClick(R.id.recruit_popup_register)
    public void onRegister() {
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @OnClick(R.id.recruit_popup_exit)
    public void onExit() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            currentLocation = savedInstanceState.getParcelable(LOCATION);
            cameraPosition = savedInstanceState.getParcelable(CAMERA_POSITION);

        }
        setContentView(R.layout.activity_map_popup);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        getWindow().getAttributes().width = (int)(0.9f * display.getWidth());
        getWindow().getAttributes().height = (int)(0.9f * display.getHeight());
        ButterKnife.bind(this);
        mapNetworkService = AppController.getInstance().getGoogleNetworkService();
        buildGoogleApiClient();
        List<String> initResultList = new ArrayList<>();
        placeResults = new ArrayList<>();
        initResultList.add("검색 결과 아직 없음.");
        listAdapter = new ArrayAdapter(getApplicationContext(), R.layout.recruit_popup_search_result_list, initResultList);
        searchListView.setAdapter(listAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (placeResults.size() == 0) {
                    return;
                }
                PlacesResults.PlaceResult selectedPlace = placeResults.get(position);
                moveCameraToLatLng(selectedPlace.getLat(), selectedPlace.getLng(), selectedPlace.getName());
                resultIntent.putExtra("address", selectedPlace.getFormattedAddress() + " " + selectedPlace.getName());
                resultIntent.putExtra("lat", selectedPlace.getLat());
                resultIntent.putExtra("lng", selectedPlace.getLng());
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onSearch();
                }
                return false;
            }
        });
        getDevicePermission();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
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
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        MapFragment mapFragment =
                (MapFragment) getFragmentManager().findFragmentById(R.id.recruit_popup_map);
        mapFragment.getMapAsync(MapPopupActivity.this);

    }

    private void getDevicePermission() {
        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setRationaleConfirmText("확인")
                .setRationaleMessage("\"Freety\"의 다음 작업을 허용하시겠습니까? 이 기기의 위치에 액세스하기")
                .setDeniedMessage("거부하시면 볼수 없는데...")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
        Log.i(TAG, "getDevicePermission: " + googleApiClient.isConnected());
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (isPermissionGranted) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, getLocationRequest(), this);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            googleMap.setMyLocationEnabled(false);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        if (cameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
            cameraPosition = googleMap.getCameraPosition();
            googleMap.addMarker(new MarkerOptions().position(currentLatLng).title("현재 위치"));
            resultIntent.putExtra("lat", currentLocation.getLatitude());
            resultIntent.putExtra("lng", currentLocation.getLongitude());
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

    @OnClick(R.id.recruit_popup_search)
    void onSearch() {
        final String searchText = searchEditText.getText().toString();
        Log.i(TAG, "onSearch: searchText is : " + searchText);
        final Call<PlacesResults> requestPlacesResults = mapNetworkService.getLocationResults(searchText);
        requestPlacesResults.enqueue(new Callback<PlacesResults>() {
            @Override
            public void onResponse(Call<PlacesResults> call, Response<PlacesResults> response) {
                if (response.isSuccessful()) {
                    List<String> resultList = new ArrayList<>();
                    placeResults = response.body().getResults();
                    for (int index = 0; index < placeResults.size(); index++) {
                        PlacesResults.PlaceResult result = placeResults.get(index);
                        String fullAddress = result.getFormattedAddress() + " " + result.getName();
                        resultList.add(fullAddress);
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

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        int result = AppController.getInstance().popPageStack();
        if (result == 0) {
            Toast.makeText(this, "한 번 더 터치하시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }  else if (result < 0) {
            ActivityCompat.finishAffinity(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
            googleApiClient.stopAutoManage(this);
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private LocationRequest getLocationRequest() {
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return request;
    }

    public void onScreenClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

}
