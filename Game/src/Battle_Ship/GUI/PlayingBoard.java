package Battle_Ship.GUI;

import Battle_Ship.Board.Board;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PlayingBoard {
    public static JFrame jfrm = new JFrame();
    private BoardGUI boardEnemy = new BoardGUI(500, 1);
    private BoardGUI boardPlayer = new BoardGUI(250, 2);
    private JPanel chat = new chatGUI();

    public void setBoard(String typeBoard, BoardGUI board){
        if (typeBoard.equals("Player"))
        {
            boardPlayer.copyDetail(board);
        }
        else {
            boardEnemy.copyDetail(board);
        }
    }

    public PlayingBoard(BoardGUI m_boardEnemy/*, Board inforBoardP*/){
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Container c = jfrm.getContentPane();
        jfrm.setBounds(300, 90, 950, 700);
        jfrm.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jfrm.setResizable(false);
        //setBoard("Player", inforBoardP);
        setBoard("Enemy", m_boardEnemy);
        boardEnemy.setStatus(1);

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 40,10);
        c.setLayout(layout);
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(500,650));
        BoxLayout boxlayout_time = new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS);
        leftPanel.setLayout(boxlayout_time);
        JPanel time = new TimeGUI();
        time.setMaximumSize(new Dimension(500,50));
        leftPanel.add(time);
        leftPanel.add(boardEnemy);
        leftPanel.add(Box.createRigidArea(new Dimension(0,20)));

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(350,650));
        BoxLayout boxLayout_right = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
        rightPanel.setLayout(boxLayout_right);
        //rightPanel.setBorder(blackline);
        rightPanel.add(boardPlayer);
        boardPlayer.setPreferredSize(new Dimension(300,300));
        rightPanel.add(Box.createRigidArea(new Dimension(0,80)));
        //boardPlayer.setBorder(blackline);
        rightPanel.add(chat);


        c.add(leftPanel);
        c.add(rightPanel);
        jfrm.setVisible(true);
    }

    /*public static void main(String[] args) throws Exception{
        PlayingBoard f = new PlayingBoard();
    }*/
}
