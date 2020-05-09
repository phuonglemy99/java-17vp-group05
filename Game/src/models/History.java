package models;

import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("enemy")
    private String enemy;

    @SerializedName("status")
    private Boolean status;

    @SerializedName("time")
    private String time;

    public History(String enemy, Boolean status, String time) {
        this.enemy = enemy;
        this.status = status;
        this.time = time;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
