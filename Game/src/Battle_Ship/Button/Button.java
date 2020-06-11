package Battle_Ship.Button;
import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String title){
        this.setText(title);
        this.setFont(new Font("Arial", Font.PLAIN, 15));
        this.setMaximumSize(new Dimension(150,60));
    }
}
