package com.sopt.freety.freety.application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kakao.auth.KakaoSDK;
import com.sopt.freety.freety.network.MapNetworkService;
import com.sopt.freety.freety.network.NaverNetworkService;
import com.sopt.freety.freety.network.NetworkService;
import com.sopt.freety.freety.view.login.adapter.KakaoSDKAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cmslab on 6/25/17.
 */

public class AppController extends Application {

    private static AppController instance;
    private NetworkService networkService;
    private MapNetworkService googleNetworkService;
    private NaverNetworkService naverNetworkService;
    private static volatile Activity currentActivity = null;
    private int pageStackCounter = 0;
    private long pageStackMillis;

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

    public MapNetworkService getGoogleNetworkService() {
        if (googleNetworkService == null) {
            googleNetworkService = buildGoogleNetworkService();
        }
        return googleNetworkService;
    }

    public NaverNetworkService getNaverNetworkService() {
        if (naverNetworkService == null) {
            naverNetworkService = buildNaverNetworkService();
        }
        return naverNetworkService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppController.instance = this;
        instance = this;
        Realm.init(this);
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

    private MapNetworkService buildGoogleNetworkService() {
        final Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(MapNetworkService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MapNetworkService.class);
    }

    private NaverNetworkService buildNaverNetworkService() {
        final Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(NaverNetworkService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NaverNetworkService.class);
    }

    public int popPageStack() {
        if (pageStackCounter == 0 && System.currentTimeMillis() - pageStackMillis < 2000) {
            return -1;
        } else if (pageStackCounter == 0) {
            pageStackMillis = System.currentTimeMillis();
            return 0;
        } else {
            pageStackCounter--;
        }
        return 1;
    }

    public void pushPageStack() {
        if (pageStackCounter < 3) {
            pageStackCounter++;
        }
    }

    public void resetPageStack() {
        pageStackCounter = 0;
    }

}
