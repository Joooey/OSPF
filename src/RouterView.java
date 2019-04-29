

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

/**
 * @author dmrfcoder
 * @date 2019-04-17
 */
public class RouterView extends JComponent implements IMomentsView {

    private int momentsMaxCount;

    private volatile Vector<Moment> moments;

    private int windowHeight;
    private int windowWidth;

    private String title;
    private int textHeight = 40;

    private volatile int curCount;

    private int circleD;

    private CirclePosition circlePosition;

    private int circleX = 0;
    private int circleY = 0;
    //router矩形坐标
    private int rectX = 0;
    private int rectY = 0;
    //router矩形宽高
    private int rectW;
    private int rectH;
    private int Padding = 10;

    public int getCircleX() {
        return circleX;
    }

    public int getCircleY() {
        return circleY;
    }

    public Point getCircleCenter() {
        return new Point(getX() + circleX + circleD / 2, getY() + circleY + circleD + Padding);

    }

    public void setBounds(Point point) {
        super.setBounds(point.x, point.y, windowWidth + 1, windowHeight + 1);
    }

    public RouterView(String title, CirclePosition circlePosition) {
        this.title = title;
        this.momentsMaxCount = 6;
        moments = new Vector<>();

        circleD = 50;
        this.circlePosition = circlePosition;
        initView();
    }


    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    @Override
    public void initView() {

        rectH = (momentsMaxCount + 1) * textHeight;
        rectW = ViewConfigure.defaultRouterViewW;
        //定义圆圈和矩形的位置关系
        switch (circlePosition) {
            case TOP:

                windowHeight = rectH + circleD + Padding;
                windowWidth = rectW;
                circleX = (windowWidth - circleD) / 2;
                circleY = 0;
                rectX = 0;
                rectY = circleD + Padding;

                break;
            case LEFT:

                windowHeight = rectH;
                windowWidth = rectW + circleD + Padding;
                circleX = 0;
                circleY = (windowHeight - circleD) / 2;
                rectX = circleD + Padding;
                rectY = 0;
                break;
            case RIGHT:

                windowHeight = rectH;
                windowWidth = rectW + circleD + Padding;
                circleX = windowWidth - circleD;
                circleY = (windowHeight - circleD) / 2;
                rectX = 0;
                rectY = 0;
                break;
            case BOTTOM:

                windowHeight = rectH + circleD + Padding;
                windowWidth = rectW;
                circleX = (windowWidth - circleD) / 2;
                circleY = windowHeight - circleD;
                rectX = 0;
                rectY = 0;
                break;
            default:
                break;
        }

        Moment moment = new Moment("      目的地址        费用      下一跳");
        addMoment(moment);


    }


    @Override
    public void addMoment(Moment moment) {
        if (curCount < momentsMaxCount) {
            moments.add(moment);
            curCount++;
        } else {
            moments.remove(0);
            curCount--;

            moments.add(moment);
            curCount++;

        }
        repaint();
    }

    @Override
    public void replaceMoments(Vector<Moment> moments) {
        this.moments.clear();
        this.moments.addAll(moments);
        curCount=moments.size();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(ViewConfigure.defaultTextColor);
        g.drawRect(rectX, rectY, rectW, rectH);


        for (int index = 0; index < curCount; index++) {
            int itemY = rectY + textHeight * (index + 1);

            try {

                g.setColor(ViewConfigure.defaultTextColor);
                g.setFont(new Font("微软雅黑", Font.PLAIN, 16));

                g.drawString(moments.get(index).getMomentContent(), 5 + rectX, itemY);
            } catch (Exception e) {
                System.out.println("Exception-MomentView-paint:" + e.getLocalizedMessage());
            }

        }
        g.setColor(ViewConfigure.defaultTextColor);


        g.fillOval(circleX, circleY, circleD, circleD);

        g.setColor(Color.BLACK);
        g.drawString(title, circleX + circleD / 2, circleY + circleD / 2);


    }


}
