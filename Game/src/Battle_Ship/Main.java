package Battle_Ship;

import Battle_Ship.GUI.Login;
import Battle_Ship.GUI.PlayingBoard;
import Battle_Ship.GUI.PreparingBoard;
import Battle_Ship.GUI.SignUp;
import io.socket.client.Socket;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        SocketPlayer socket = SocketPlayer.getInstance();
        socket.connect();
    }
}
