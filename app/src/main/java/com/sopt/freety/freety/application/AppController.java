package com.sopt.freety.freety.application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kakao.auth.KakaoSDK;
import com.sopt.freety.freety.network.MapNetworkService;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.view.login.adapter.KakaoSDKAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cmslab on 6/25/17.
 */

public class AppController extends Application {

    private static AppController instance;
    private NetworkService networkService;
    private MapNetworkService mapNetworkService;
    //private static GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;

    public static Activity getCurrentActivity() {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        AppController.currentActivity = currentActivity;
    }

    /**
     * singleton
     * @return singleton
     */
    public static AppController getAppControllerContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return instance;
    }

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
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
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
