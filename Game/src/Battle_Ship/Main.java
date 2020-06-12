package Battle_Ship;

import Battle_Ship.GUI.Login;
import Network.Connection.GetDataService;
import Network.Connection.RetrofitClientInstance;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        (new Login()).setVisible(true);

    }
}
