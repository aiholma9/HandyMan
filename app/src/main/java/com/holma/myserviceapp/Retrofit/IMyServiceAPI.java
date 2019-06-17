package com.holma.myserviceapp.Retrofit;



import com.holma.myserviceapp.Model.UpdateUserModel;
import com.holma.myserviceapp.Model.User;
import com.holma.myserviceapp.Model.UserModel;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IMyServiceAPI {
    @GET("user")
    Observable<UserModel> getUser(@Query("key") String apiKey,
                                  @Query("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("user")
    Observable<UpdateUserModel> updateUserInfo(@Field("key") String apiKey,
                                               @Field("userName") String userName,
                                               @Field("userPhone") String userPhone,
                                               @Field("userAddress") String userAddress,
                                               @Field("fbid") String fbid);

}