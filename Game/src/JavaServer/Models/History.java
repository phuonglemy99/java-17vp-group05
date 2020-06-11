package JavaServer.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class History {
    @SerializedName("enemy")
    private String enemy;

    @SerializedName("status")
    private String status;

    @SerializedName("time")
    private String time;

    public History(String enemy, String status, String time) {
        this.enemy = enemy;
        this.status = status;
        this.time = time;
    }

    public void printHistory(){
        System.out.println("Time: " + time);
        System.out.println("Enemy: " + enemy);
        System.out.println("Status: " + status);
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
