package Battle_Ship.GUI;

import Battle_Ship.Button.Button;
import Battle_Ship.Ship.Ship;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PreparingBoard extends JPanel{
    public JFrame jfrm = new JFrame();
    public BoardGUI board = new BoardGUI(500, 0);
    public PreparingBoard(){
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Container c = jfrm.getContentPane();
        jfrm.setBounds(300, 90, 950, 700);
        jfrm.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jfrm.setResizable(false);

        FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 50,10);
        c.setLayout(layout);

        JPanel leftPanel = new JPanel();
        //leftPanel.setBorder(blackline);
        leftPanel.setPreferredSize(new Dimension(600,650));
        BoxLayout boxlayout_time = new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS);
        leftPanel.setLayout(boxlayout_time);
        JPanel time = new TimeGUI();
        time.setMaximumSize(new Dimension(650,50));

        //time.setBorder(blackline);
        leftPanel.add(time);
        leftPanel.add(board);
        leftPanel.add(Box.createRigidArea(new Dimension(0,20)));
        JButton save = new JButton("Save");
        leftPanel.add(save);
        save.addActionListener(new saveButton());

        JPanel buttonPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS);
        buttonPanel.setLayout(boxlayout);
        //buttonPanel.setBorder(blackline);
        buttonPanel.setPreferredSize(new Dimension(150,650));

        Button battleShip = new Button("Battle Ship");
        Button carrierShip = new Button("Carrier Ship");
        Button destroyer = new Button("Destroyer");
        Button patrolBoat = new Button("Patrol Boat");
        Button submarine = new Button("Submarine");

        battleShip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setFlag(Ship.type.Battle);
                board.setChooseType(true);
            }
        });

        carrierShip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setFlag(Ship.type.Carrier);
                board.setChooseType(true);
            }
        });

        destroyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setFlag(Ship.type.Destroyer);
                board.setChooseType(true);
            }
        });

        submarine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setFlag(Ship.type.Submarine);
                board.setChooseType(true);
            }
        });

        patrolBoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setFlag(Ship.type.Patrol);
                board.setChooseType(true);
            }
        });

        buttonPanel.add(Box.createRigidArea(new Dimension(0,20)));
        buttonPanel.add(battleShip);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,60)));
        buttonPanel.add(carrierShip);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,60)));
        buttonPanel.add(destroyer);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,60)));
        buttonPanel.add(patrolBoat);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,60)));
        buttonPanel.add(submarine);


        c.add(leftPanel);
        c.add(buttonPanel);
        jfrm.setVisible(true);
    }

    private class saveButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            board.saveShip();
            // TODO: change scene
            jfrm.setVisible(false);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new PlayingBoard(board);
                }
            });
        }
    }

    public static void main(String[] args) throws Exception{
        PreparingBoard f = new PreparingBoard();
    }
}
