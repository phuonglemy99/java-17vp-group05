package JavaServer;

import JavaServer.Models.History;
import JavaServer.Models.User;
import JavaServer.Network.GetDataService;
import JavaServer.Network.RetrofitClientInstance;
import JavaServer.ResponseResult.StatusResult;
import JavaServer.ResponseResult.UserLoginResult;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class main {
    public static void main(String[] args){
        // Create service to call API
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        // Declare call
        Call<UserLoginResult> call = service.loginUser("user01", "123456");

        try {
            Response<UserLoginResult> response = call.execute();
            UserLoginResult userLoginResult = response.body();

            if (userLoginResult.status == 1 && userLoginResult.activate == 1) {
                System.out.println("Login Successfully");
                Call<User> callGetInfoUser = service.getAllInfoUser(userLoginResult.userid);
                Response<User> res = callGetInfoUser.execute();
                //Add history
                Call<History> callAddHistory = service.addHistory(userLoginResult.userid,
                        "11/12/1999", "Player2", "win");
                Response<History> res_his = callAddHistory.execute();
                User user = res.body();
                History history = res_his.body();
                user.printUser();
                System.out.println("After changing: ");
                Call<StatusResult> updated = service.updateInfo(userLoginResult.userid, "LAMP_1", "123456");
                Response<StatusResult> res_update = updated.execute();
                StatusResult status = res_update.body();
                if (status.status == 1){
                    System.out.println("Change successfully");
                }
                else{
                    System.out.println("Can't change!");
                }
                System.out.println("Get Successfully");
            } else {
                System.out.println("Login Failed");
            }

            /*Call<StatusResult> call_1 = service.createUser("LAMP", "user01", "123456");
            Response<StatusResult> reponse_1 = call_1.execute();
            StatusResult statusResult = reponse_1.body();

            if (statusResult.status == 1)
                System.out.println("Create Successfully");
            else
                System.out.println("Create Unsuccessfully");*/

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
