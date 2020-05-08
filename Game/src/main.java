import models.User;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class main {
    static public void main(String[] args){

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<User>> call = service.getAllPhotos();

        try{
            Response<List<User>> response = call.execute();
            List<User> users = response.body();
            for (int i = 0; i < 5; i++) {
                System.out.println("ID: " + users.get(i).getId());
                System.out.println("Title: " + users.get(i).getTitle());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
