import ResponseResult.StatusResult;
import ResponseResult.UserLoginResult;
import models.User;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class main {
    static public void main(String[] args){
        // Create service to call API
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        // Declare call
        Call<UserLoginResult> call = service.loginUser("user01", "123456");


        try {
            Response<UserLoginResult> response = call.execute();
            UserLoginResult userLoginResult = response.body();

            if (userLoginResult.status == 1) {
                System.out.println("Login Successfully");
                Call<User> callGetInfoUser = service.getAllInfoUser(userLoginResult.userid);
                Response<User> res = callGetInfoUser.execute();
                User user = res.body();
                System.out.println("Get Successfully");
            } else {
                System.out.println("Login Failed");
            }

            Call<StatusResult> call_1 = service.createUser("LAMP", "user3", "123123");
            Response<StatusResult> reponse_1 = call_1.execute();
            StatusResult statusResult = reponse_1.body();

            if (statusResult.status == 1)
                System.out.println("Create Successfully");
            else
                System.out.println("Create Unsuccessfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }
}
