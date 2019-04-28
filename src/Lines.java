import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;

/**
 * @author Joey
 * @date 2019/4/27
 */
public class Lines extends JComponent {
    private Point point1;
    private Point point2;

    public Lines(RouterView[] routerView, int router1Index, int router2Index) {
        this.point1 = routerView[router1Index].getCircleCenter();
        this.point2 = routerView[router2Index].getCircleCenter();
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }
}
