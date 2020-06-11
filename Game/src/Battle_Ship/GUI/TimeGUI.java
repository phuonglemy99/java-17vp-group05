package Battle_Ship.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;


public class TimeGUI extends JPanel {
    private Timer timer;
    private long startTime = -1;
    private long duration = 60000;
    private JLabel label;

    public TimeGUI() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(40,40));
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startTime < 0) {
                    startTime = System.currentTimeMillis();
                }
                long now = System.currentTimeMillis();
                long clockTime = now - startTime;
                if (clockTime >= duration) {
                    clockTime = duration;
                    timer.stop();
                }
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                label.setText(df.format(duration - clockTime));
            }
        });
        if (!timer.isRunning()) {
            startTime = -1;
            timer.start();
        }
        timer.setInitialDelay(0);
        label = new JLabel("01:00");
        add(label);
    }
}
