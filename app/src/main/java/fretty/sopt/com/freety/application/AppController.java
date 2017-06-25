package fretty.sopt.com.freety.application;

import android.app.Application;

import fretty.sopt.com.freety.network.NetworkService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cmslab on 6/25/17.
 */

public class AppController extends Application {
    private static final String baseURL = "http";

    private static AppController instance;
    private NetworkService networkService;

    public static AppController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        if (networkService == null) {
            networkService = buildNetworkService();
        }

        return networkService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppController.instance = this;
    }

    private NetworkService buildNetworkService() {
        final Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NetworkService.class);
    };
}
