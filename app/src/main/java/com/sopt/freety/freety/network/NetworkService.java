package com.sopt.freety.freety.network;

import com.sopt.freety.freety.view.login.JoinResult;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by cmslab on 6/25/17.
 */

public interface NetworkService {


    @Multipart
    @POST("/")
    Call<JoinResult> registerPersonData(@Part("email") RequestBody email,
                                        @Part("pwd") RequestBody pwd,
                                        @Part("name") RequestBody name,
                                        @Part("age") RequestBody age,

                                        @Part("belongName") RequestBody belongName
                                       );


}
