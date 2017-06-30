package com.sopt.freety.freety.application;

import android.app.Application;

import com.sopt.freety.freety.network.MapNetworkService;
import com.sopt.freety.freety.network.NetworkService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cmslab on 6/25/17.
 */

public class AppController extends Application {

    private static AppController instance;
    private NetworkService networkService;
    private MapNetworkService mapNetworkService;

    public static AppController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        if (networkService == null) {
            networkService = buildNetworkService();
        }
        return networkService;
    }

    public MapNetworkService getMapNetworkService() {
        if (mapNetworkService == null) {
            mapNetworkService = buildMapNetworkService();
        }

        return mapNetworkService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppController.instance = this;
    }

    private NetworkService buildNetworkService() {
        final Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(NetworkService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NetworkService.class);
    };

    private MapNetworkService buildMapNetworkService() {
        final Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(MapNetworkService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MapNetworkService.class);
    }
}
