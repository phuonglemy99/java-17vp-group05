package Battle_Ship;

import Battle_Ship.GUI.DashBoard;
import Battle_Ship.GUI.PlayingBoard;
import Battle_Ship.GUI.chatGUI;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

import static Battle_Ship.GUI.PlayingBoard.jfrm;

public class SocketPlayer {
    private static String SOCKET_URL = "http://localhost:3000/play-match";
    private static SocketPlayer instance;
    private Socket socket;
    private String socketID;

    // Lớp client socket
    private SocketPlayer() throws URISyntaxException {
        socket = IO.socket(SOCKET_URL);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("Da ket noi socket thanh cong");
            }

        }).on("info", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                socketID = objects[0].toString();
            }
        }).on("chat message", new PlayingBoard.ChatEmitterListener()
        ).on("startGame", new PlayingBoard.StartGameEmitterListener()
        ).on("firstTurn", new PlayingBoard.WaitTurnEmitterListener()
        ).on( "resultFire", new PlayingBoard.ResultFireEmitterListener()
        ).on("enemyFire",new PlayingBoard.EnemyFireEmitterListener()
        ).on("endGame", new Emitter.Listener(){
            @Override
            public void call(Object... objects) {
                System.out.println("Ket thuc tran dau");
                PlayingBoard.jfrm.setVisible(false);
                (new DashBoard()).setVisible(true);
                socket.disconnect();
                socket = null;
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Thoat ket noi");
            }
        });
    }

    public static SocketPlayer getInstance(){
        if (instance == null) {
            try {
                instance = new SocketPlayer();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void connect(){
        socket.connect();
    }

    public boolean checkSocketNotNull(){
        return socket.connected();
    }

    public void emit(String event, Object... args){
        socket.emit(event, args);
    }

    public String getSocketID() {
        return socketID;
    }
}
