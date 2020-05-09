package ResponseResult;

import com.google.gson.annotations.SerializedName;

// Phan tich file JSON thanh doi tuong

public class UserLoginResult {
    @SerializedName("status")
    public int status;

    @SerializedName("userid")
    public String userid;
}
