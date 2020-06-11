package Battle_Ship;

import Battle_Ship.GUI.Login;
import Battle_Ship.GUI.PreparingBoard;
import Battle_Ship.GUI.SignUp;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Login login = new Login();
        login.setVisible(true);
        /*SignUp signup = new SignUp();
        signup.setVisible(true);*/

    }
}
