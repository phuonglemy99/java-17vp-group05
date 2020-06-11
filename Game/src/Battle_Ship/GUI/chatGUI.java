package Battle_Ship.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class chatGUI extends JPanel{
    JTextArea conversation;
    JTextField chat;

    public chatGUI(){
        setPreferredSize(new Dimension(350,400));
        conversation = new JTextArea(15,30);
        conversation.setLineWrap(true);
        conversation.setWrapStyleWord(true);
        conversation.setEditable(false);
        JScrollPane qScroller = new JScrollPane(conversation);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chat = new JTextField(20);
        JButton sendButton = new JButton("Send");
        //sendButton.addActionListener(new SendButtonListener());
        add(qScroller);
        add(chat);
        add(sendButton);
    }
}
