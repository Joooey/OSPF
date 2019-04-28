import javax.swing.*;
import java.awt.*;


public class CircleButton extends JLabel {


    private int D;


    public CircleButton(int D) {
        this.D = D;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(ViewConfigure.defaultTextColor);
        g.fillOval((getWidth() - D) / 2, (getHeight() - D) / 2, D, D);


        String text = "";
        g.setColor(Color.BLACK);
        text = "SEND";

        g.drawString(text, (getWidth() - 25) / 2, 5 + getHeight() / 2);

    }


}