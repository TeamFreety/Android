package com.sopt.freety.freety.network;

import android.Manifest;

import com.sopt.freety.freety.view.login.JoinResult;
import com.sopt.freety.freety.view.login.data.DuplicateData;
import com.sopt.freety.freety.view.login.data.SignUpData;
import com.sopt.freety.freety.view.login.data.SignUpResultData;
import com.sopt.freety.freety.view.recruit.data.PostDetailResultData;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cmslab on 6/25/17.
 */

public interface NetworkService {

    public static final String BASE_URL = "http://52.79.172.131:3000";

    @Multipart
    @POST("/")
    Call<JoinResult> registerDesignerData(@Part("email") RequestBody email,
                                          @Part("pwd") RequestBody pwd,
                                          @Part("name") RequestBody name,
                                          @Part("age") RequestBody age,
                                          @Part("belong") RequestBody belong,
                                          @Part("belongName") RequestBody belongName,
                                          @Part("career") RequestBody career);

    @POST("/signup/designer/email")
    Call<SignUpResultData> registerDesignerData(@Body SignUpData signUpData);

    @POST("/signup/model/email")
    Call<SignUpResultData> registerModelData(@Body SignUpData signUpData);

    @GET("/duplicateCheck")
    Call<DuplicateData> checkDuplicate(@Query("tempEmail") String email);

    @GET("/postDetail/{postId}")
    Call<PostDetailResultData> getPostDetailData(@Header("member_token") String token, @Path("postId") int postId);

}