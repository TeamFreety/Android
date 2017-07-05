package com.sopt.freety.freety.network;

import com.sopt.freety.freety.view.search.data.PlacesResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cmslab on 6/29/17.
 */

public interface MapNetworkService {

    public static final String BASE_URL = "https://maps.googleapis.com";
    public static final String API_KEY = "AIzaSyCzezaEEraT_PEJt4VxBY5MtEGLqWrNF4U";



    @GET("/maps/api/place/textsearch/json?key=" + API_KEY + "&language=ko")
    Call<PlacesResults> getLocationResults(@Query("query") String query);
}
