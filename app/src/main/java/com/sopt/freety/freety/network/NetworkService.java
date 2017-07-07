package com.sopt.freety.freety.network;

import com.sopt.freety.freety.data.OnlyMsgResultData;
import com.sopt.freety.freety.data.PostListResultData;
import com.sopt.freety.freety.view.letter.data.LetterListResultData;
import com.sopt.freety.freety.view.letter.data.PushRequestData;
import com.sopt.freety.freety.view.login.data.DuplicateData;
import com.sopt.freety.freety.view.login.data.LoginRequestData;
import com.sopt.freety.freety.view.login.data.LoginResultData;
import com.sopt.freety.freety.view.login.data.SNSLoginRequestData;
import com.sopt.freety.freety.view.login.data.SNSLoginResultData;
import com.sopt.freety.freety.view.login.data.SignUpData;
import com.sopt.freety.freety.view.login.data.SignUpResultData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageCareerRequestData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageDesignerGetData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageModelGetData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageReviewResultData;
import com.sopt.freety.freety.view.my_page.data.network.MyPageStatusUpdateRequestData;
import com.sopt.freety.freety.view.recruit.data.PickRequestData;
import com.sopt.freety.freety.view.recruit.data.PickResultData;
import com.sopt.freety.freety.view.recruit.data.PostDetailResultData;
import com.sopt.freety.freety.view.wirte.data.WritePostResultData;
import com.sopt.freety.freety.view.wirte.data.WriteRequestData;

import java.util.List;

import okhttp3.MultipartBody;
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

    @POST("/signup/designer/email")
    Call<SignUpResultData> registerDesignerData(@Body SignUpData signUpData);

    @POST("/signup/model/email")
    Call<SignUpResultData> registerModelData(@Body SignUpData signUpData);

    @POST("/signup/designer/sns")
    Call<SignUpResultData> registerSNSDesignerData(@Body SignUpData signUpData);

    @POST("/signup/model/sns")
    Call<SignUpResultData> registerSNSModelData(@Body SignUpData signUpData);

    @GET("/duplicateCheck")
    Call<DuplicateData> checkDuplicate(@Query("tempEmail") String email);

    @POST("/logincheck")
    Call<OnlyMsgResultData> auto(@Header("member_token") String token);

    @POST("/login/email")
    Call<LoginResultData> login(@Body LoginRequestData data);

    @POST("/login/sns")
    Call<SNSLoginResultData> snslogin(@Body SNSLoginRequestData data);

    @GET("/postList")
    Call<PostListResultData> getHomePostData(@Query("sort") int type);

    @GET("/postDetail/{postId}")
    Call<PostDetailResultData> getPostDetailData(@Header("member_token") String token, @Path("postId") int postId);

    @POST("/postDetail/writePost")
    Call<WritePostResultData> writePostData(@Header("member_token") String token, @Body WriteRequestData writeRequestData);

    @Multipart
    @POST("/postDetail/writePostPhoto")
    Call<OnlyMsgResultData> uploadPhoto(@Header("member_token") String token, @Part("postId") RequestBody postId, @Part MultipartBody.Part body);

    @Multipart
    @POST("/post/write")
    Call<OnlyMsgResultData> registerPost(@Header("member_token") String token, @Body WriteRequestData data, List<RequestBody> files);

    @POST("/pick")
    Call<PickResultData> pick(@Header("member_token") String token, @Body PickRequestData data);

    @GET("/mypage/modelMypage")
    Call<MyPageModelGetData> getMyPageInModelAccount(@Header("member_token") String token);

    @GET("/mypage/designerMypage")
    Call<MyPageDesignerGetData> getMyPageInDesignerAccount(@Header("member_token") String token);

    @POST("/mypage/statusMsg")
    Call<OnlyMsgResultData> getOkMsg(@Header("member_token") String token, @Body MyPageStatusUpdateRequestData data);

    @POST("/mypage/careerText")
    Call<OnlyMsgResultData> getCareerMsg(@Header("member_token") String token, @Body MyPageCareerRequestData data);

    @Multipart
    @POST("/mypage/memberPhoto")
    Call<OnlyMsgResultData> getOkMsgFromProfile(@Header("member_token") String token, @Part MultipartBody.Part body);

    @Multipart
    @POST("/mypage/modelPhoto1")
    Call<OnlyMsgResultData> uploadModelPhoto1(@Header("member_token") String token, @Part MultipartBody.Part body);

    @Multipart
    @POST("/mypage/modelPhoto2")
    Call<OnlyMsgResultData> uploadModelPhoto2(@Header("member_token") String token, @Part MultipartBody.Part body);

    @Multipart
    @POST("/mypage/modelPhoto3")
    Call<OnlyMsgResultData> uploadModelPhoto3(@Header("member_token") String token, @Part MultipartBody.Part body);

    @GET("/postDetail/writer/{memberId}")
    Call<MyPageDesignerGetData> getOtherDesignerMyPage(@Header("member_token") String token, @Path("memberId") int memberId);

    @GET("/letterDetail/mypage")
    Call<MyPageModelGetData> getOtherModelMyPage(@Header("member_token") String token, @Query("sent_mem_id") int memberId);

    @Multipart
    @POST("/comment/writeComment")
    Call<MyPageReviewResultData> registerReview(@Header("member_token") String token,
                                                @Part("memberId") RequestBody memberId,
                                                @Part("score") RequestBody score,
                                                @Part("title") RequestBody title,
                                                @Part("content") RequestBody content,
                                                @Part MultipartBody.Part imageBody);

    @GET("/search/latest")
    Call<PostListResultData> getSearchLatestData();

    @GET("/search/nearest")
    Call<PostListResultData> getSearchNearestData(@Query("latitude") double lat, @Query("longitude") double lng);

    @GET("/searchDetail")
    Call<PostListResultData> getFilteredData(@Query("typeDye") int typeDye,
                                             @Query("typePerm") int typePerm,
                                             @Query("typeCut") int typeCut,
                                             @Query("typeEct") int typeEtc,
                                             @Query("least_price") int leastPrice,
                                             @Query("high_price") int highPrice,
                                             @Query("career") int careerType,
                                             @Query("least_date") String leastDate,
                                             @Query("high_date") String highDate,
                                             @Query("sigugun") String sigugun);


    @GET("/letterList")
    Call<LetterListResultData> getLetterListDatas(@Header("member_token") String token);

    @GET("/letterDetail")
    Call<LetterListResultData> getLetterDatas(@Header("member_token") String token,
                                              @Query("sent_mem_id") int memberId);

    @POST("/letterInsert")
    Call<OnlyMsgResultData> pushLetter(@Header("member_token") String token, @Body PushRequestData data);


}
