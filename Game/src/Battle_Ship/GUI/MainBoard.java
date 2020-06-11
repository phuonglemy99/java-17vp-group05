package Battle_Ship.GUI;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;


class MainBoard extends JFrame
{	private JLabel ngaysinh;
    private JLabel name;
    private JLabel id ;
    private JButton btnStore =new JButton("Cửa Hàng");
    private JButton btnFindMatch =new JButton("Tìm Trận");
    private JButton btnLow =new JButton("Luật Chơi");


    public MainBoard(String title)
    {
        super(title);
        intcom();
    }
    private void intcom()
    {
        //this.setLayout(null);
        JPanel pcenter = new JPanel();
        pcenter.setLayout(null);

        name = new JLabel("Họ và Tên :");
        id = new JLabel("ID : ");
        ngaysinh = new JLabel("Ngày Sinh : ");

        id.setBounds(400, 40, 300, 30);
        id.setFont(id.getFont().deriveFont(20.0f));

        name.setBounds(400, 70, 300, 30);
        name.setFont(id.getFont().deriveFont(20.0f));

        ngaysinh.setBounds(400, 100, 300, 30);
        ngaysinh.setFont(id.getFont().deriveFont(20.0f));
        pcenter.add(id);
        pcenter.add(name);
        pcenter.add(ngaysinh);

        JPanel psouth=new JPanel();
        psouth.setLayout(new FlowLayout(1,100,40));
        psouth.add(btnStore);
        psouth.add(btnFindMatch);
        psouth.add(btnLow);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(pcenter,BorderLayout.CENTER);
        //this.getContentPane().add(pcenter,BorderLayout.CENTER);
        this.getContentPane().add(psouth,BorderLayout.SOUTH);
    }

    public static void main(String[] args)
    {
        MainBoard mf=new MainBoard("BattleShip-Mainboard");
        mf.setSize(800,500);
        mf.setVisible(true);
    }
}