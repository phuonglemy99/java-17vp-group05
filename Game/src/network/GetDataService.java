package network;

import ResponseResult.StatusResult;
import ResponseResult.UserLoginResult;
import models.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

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

    @GET("/users/{userid}/getAllInfo")
    Call<User> getAllInfoUser(@Path("userid") String userid);

}
