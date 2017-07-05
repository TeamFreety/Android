package com.sopt.freety.freety.network;

import com.sopt.freety.freety.view.wirte.data.NaverResultData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by cmslab on 7/5/17.
 */

public interface NaverNetworkService {

    public static final String BASE_URL = "https://openapi.naver.com";
    public static final String CLIENT_KEY = "1B9fVTgiXiS7zDR4ZHsr";
    public static final String SECRET_KEY = "b3ZdkTDeHI";

    @GET("/v1/map/reversegeocode")
    Call<NaverResultData> getSigugun(@Query("query") String query, @Header("X-Naver-Client-Id") String client, @Header("X-Naver-Client-Secret") String secret);
}
