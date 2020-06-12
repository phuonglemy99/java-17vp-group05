package Battle_Ship;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class SocketPlayer {
    private static String SOCKET_URL = "http://localhost:3000/play-match";
    private static SocketPlayer instance;
    private Socket socket;

    private SocketPlayer() throws URISyntaxException {
        socket = IO.socket(SOCKET_URL);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Da ket noi socket thanh cong");
            }
        }).on("chat message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(args[0]);
            }
        }).on("enemy fire",new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                // TODO: Check enemy fire right or wrong place, update UI
                int x;
                int y;

                String result = "";
                socket.emit("result", result);
            }
        }).on("end_game", new Emitter.Listener(){
            @Override
            public void call(Object... objects) {
                System.out.println("Ket thuc tran dau");
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

    public void emit(String event, Object... args){
        socket.emit(event, args);
    }
}
