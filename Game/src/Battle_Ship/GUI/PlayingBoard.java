package Battle_Ship.GUI;

import Battle_Ship.Board.Board;
import Battle_Ship.SocketPlayer;
import io.socket.emitter.Emitter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PlayingBoard {
    public  static JFrame jfrm = new JFrame();
    private static BoardGUI boardEnemy = new BoardGUI(500, 1);
    private static BoardGUI boardPlayer = new BoardGUI(250, 2);
    private static chatGUI chat = new chatGUI();
    private static DialogWait waitPlayerDialog = new DialogWait();
    public  static DialogWait waitPlayerTurn = new DialogWait();
    private SocketPlayer socketPlayer;

    public void setBoard(String typeBoard, BoardGUI board){
        if (typeBoard.equals("Player"))
        {
            boardPlayer.copyDetail(board);
        }
        else {
            boardEnemy.copyDetail(board);
        }
    }

    public PlayingBoard(BoardGUI m_boardPlayer){
        socketPlayer = SocketPlayer.getInstance();

        socketPlayer.connect();

        // Wait for socket is connected
        while(!socketPlayer.checkSocketNotNull());

        Border blackline = BorderFactory.createLineBorder(Color.black);
        Container c = jfrm.getContentPane();
        jfrm.setBounds(300, 90, 950, 700);
        jfrm.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jfrm.setResizable(false);
        //setBoard("Player", inforBoardP);
        setBoard("Player", m_boardPlayer);
        boardPlayer.setStatus(2);
        boardEnemy.setStatus(1);
        boardPlayer.showShip();

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 40,10);
        c.setLayout(layout);

        // Left Area
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(500,650));
        BoxLayout boxlayout_time = new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS);
        leftPanel.setLayout(boxlayout_time);
        JPanel time = new TimeGUI();
        time.setMaximumSize(new Dimension(500,50));
        leftPanel.add(time);
        leftPanel.add(boardEnemy);
        leftPanel.add(Box.createRigidArea(new Dimension(0,20)));


        // Right area
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(350,650));
        BoxLayout boxLayout_right = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
        rightPanel.setLayout(boxLayout_right);
        rightPanel.add(boardPlayer);
        boardPlayer.setPreferredSize(new Dimension(300,300));
        rightPanel.add(Box.createRigidArea(new Dimension(0,80)));
        rightPanel.add(chat);

        c.add(leftPanel);
        c.add(rightPanel);
        jfrm.setVisible(true);

        // Waiting: StartGame event
        waitPlayerDialog.makeWait("Waiting for other player ready", jfrm);


        waitPlayerTurn.makeWait("Waiting for Enemy turn", jfrm);
    }

    public static class ResultFireEmitterListener implements Emitter.Listener {
        @Override
        public void call(Object... objects) {
            Board.resultFire result = Board.resultFire.valueOf(objects[0].toString());
            if (result == Board.resultFire.Hit) {
                JOptionPane.showMessageDialog(jfrm, "Hit a tile of ship!");
            } else if (result == Board.resultFire.Miss) {
                JOptionPane.showMessageDialog(jfrm, "Miss");
            } else if (result == Board.resultFire.Win) {
                JOptionPane.showMessageDialog(jfrm, "You win!");
            } else {
                JOptionPane.showMessageDialog(jfrm, "Defeat a ship!");
            }
        }
    }

    public static class EnemyFireEmitterListener implements Emitter.Listener {
        @Override
        public void call(Object... objects) {
            int r = Integer.parseInt(objects[0].toString());
            int c = Integer.parseInt(objects[1].toString());
            Board.resultFire resultFindShip = boardPlayer.findShip(r, c);
            SocketPlayer.getInstance().emit("result", resultFindShip.toString());
            PlayingBoard.waitPlayerTurn.close();
        }
    }

    public static class ChatEmitterListener implements Emitter.Listener {
        @Override
        public void call(Object... objects) {
            chat.addText(objects[0].toString(), objects[1].toString());
        }
    }

    public static class WaitTurnEmitterListener implements  Emitter.Listener {
        @Override
        public void call(Object... objects) {
            PlayingBoard.waitPlayerTurn.close();
        }
    }

    public static class StartGameEmitterListener implements  Emitter.Listener {
        @Override
        public void call(Object... objects) {
            PlayingBoard.waitPlayerDialog.close();
        }
    }


}
