package com.sopt.freety.freety.network;

import com.sopt.freety.freety.data.OnlyMsgResultData;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.view.login.JoinResult;
import com.sopt.freety.freety.view.login.data.DuplicateData;
import com.sopt.freety.freety.view.login.data.SignUpData;
import com.sopt.freety.freety.view.login.data.SignUpResultData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageModelGetData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageStatusUpdateRequestData;
import com.sopt.freety.freety.view.recruit.data.PickRequestData;
import com.sopt.freety.freety.view.recruit.data.PickResultData;
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

    @GET("/postList")
    Call<PostListResultData> getHomePostData(@Query("sort") int type);

    @GET("/postDetail/{postId}")
    Call<PostDetailResultData> getPostDetailData(@Header("member_token") String token, @Path("postId") int postId);

    @POST("/pick")
    Call<PickResultData> pick(@Header("member_token") String token, @Body PickRequestData data);

    @GET("/mypage/modelMypage")
    Call<MyPageModelGetData> getMyPageModel(@Header("member_token") String token);

    @GET("/mypage/designerMypage")
    Call<MyPageDesignerGetData> getMyPageDesigner(@Header("member_token") String token);

    @POST("/mypage/statusMsg")
    Call<OnlyMsgResultData> getOkMsg(@Header("member_token") String token, @Body MyPageStatusUpdateRequestData data);

    @GET("/search/latest")
    Call<PostListResultData> getSearchLatestData();

    @GET("/search/nearest")
    Call<PostListResultData> getSearchNearestData(@Query("latitude") double lat, @Query("longitude") double lng);

    @GET("/search/")
    Call<PostListResultData> getFilteredData();
}