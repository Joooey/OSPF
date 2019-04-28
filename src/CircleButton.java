import javax.swing.*;
import java.awt.*;


public class CircleButton extends JLabel {


    private int D;
    private String text;


    public CircleButton(int D, String text) {
        this.D = D;
        this.text = text;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(ViewConfigure.defaultTextColor);
        g.fillOval((getWidth() - D) / 2, (getHeight() - D) / 2, D, D);


        g.setColor(Color.BLACK);

        g.drawString(text, (getWidth() - 25) / 2, 5 + getHeight() / 2);

    }


}