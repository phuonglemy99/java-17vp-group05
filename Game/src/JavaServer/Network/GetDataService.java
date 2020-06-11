package JavaServer.Network;

import JavaServer.Models.History;
import JavaServer.Models.User;
import JavaServer.ResponseResult.StatusResult;
import JavaServer.ResponseResult.UserLoginResult;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;

public interface GetDataService {
    //Login
    @POST("/auth/login")
    @FormUrlEncoded
    Call<UserLoginResult> loginUser(@Field("username") String username, @Field("password") String password);

    // Create User
    @POST("/users/create")
    @FormUrlEncoded
    Call<StatusResult> createUser(@Field("name") String name,
                                  @Field("username") String username,
                                  @Field("password") String password);

    @POST("/users/{userID}/addHistory")
    @FormUrlEncoded
    Call<History> addHistory(@Path("userID") String userID,
                             @Field("time") String time,
                             @Field("enemy") String enemy,
                             @Field("status") String status);

    @GET("/users/{userid}/getAllInfo")
    Call<User> getAllInfoUser(@Path("userid") String userid);

    @POST("/users/{userID}/updateInfo")
    @FormUrlEncoded
    Call<StatusResult> updateInfo(@Path("userID") String userID,
                             @Field("name") String name,
                             @Field("password") String password);

}
