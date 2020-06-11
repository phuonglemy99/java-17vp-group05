package Network.ResponseResult;


// Phan tich file JSON thanh doi tuong

import com.google.gson.annotations.SerializedName;

public class UserLoginResult {
    @SerializedName("status")
    public int status;

    @SerializedName("userid")
    public String userid;

    @SerializedName("activate")
    public Integer activate;
}
