package com.sopt.freety.freety.network;

import com.sopt.freety.freety.view.wirte.data.NaverResultData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by cmslab on 7/5/17.
 */

public interface NaverNetworkService {

    public static final String BASE_URL = "https://openapi.naver.com";
    public static final String CLIENT_KEY = "yE3kmOeFdzXfCod3n8PY";
    public static final String SECRET_KEY = "v7xzHiAw3Z";

    @Headers({"X-Naver-Client-Id: yE3kmOeFdzXfCod3n8PY", "X-Naver-Client-Secret: v7xzHiAw3Z"})
    @GET("/v1/map/reversegeocode")
    Call<NaverResultData> getSigugun(@Query("query") String query);
}
