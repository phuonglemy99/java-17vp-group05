package Battle_Ship.GUI;

import javax.swing.*;
import java.awt.*;

public class DialogWait {
    private  JDialog dialog;

    public void makeWait(String msg, Frame frm) {
        dialog = new JDialog(frm, msg, Dialog.ModalityType.APPLICATION_MODAL);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel(msg), BorderLayout.PAGE_START);
        dialog.setUndecorated(true);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frm);
        dialog.setVisible(true);
    }

    public void close() {
        dialog.dispose();
    }
}
