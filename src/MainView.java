import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainView extends JFrame implements IMainView {


    private CircleButton circleButton;
    private RouterView[] routerViews;
    private Point[] circleRouterPoints;
    private Lines[] lines;
    private int LittleRectNums=8;
    private int LineNums=4;
    private int LittleRectWidth = 10;
    private int LittleRectX[] = new int[LittleRectNums];
    private int LittleRectY[] = new int[LittleRectNums];
    private int LittleRectXDistance[] = new int[LittleRectNums];
    private int LittleRectYDistance[] = new int[LittleRectNums];

    private ExecutorService executorService;

    private MainModel mainModel;


    public MainView() throws HeadlessException {
        initData();
        initView();

        initListener();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);


        g.setColor(Color.ORANGE);

        for (int index = LineNums; index < LittleRectNums; index++) {
            g.fillRect(LittleRectX[index] + lines[index-LineNums].getPoint2().x, LittleRectY[index] + lines[index-LineNums].getPoint2().y, LittleRectWidth, LittleRectWidth);
        }

        g.setColor(ViewConfigure.defaultTextColor);
        for (int index = 0; index < LineNums; index++) {
            g.drawLine(lines[index].getPoint1().x, lines[index].getPoint1().y, lines[index].getPoint2().x, lines[index].getPoint2().y);
            g.fillRect(LittleRectX[index] + lines[index].getPoint1().x, LittleRectY[index] + lines[index].getPoint1().y, LittleRectWidth, LittleRectWidth);

        }

    }

    public void setBackGround() {
        ((JPanel) this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("C:\\Courses\\Computer Network\\OSPF\\res\\background.png");
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
    }


    @Override
    public void initData() {
        mainModel = new MainModel();
        routerViews = new RouterView[RouterConfigure.routerCount];
        circleRouterPoints = new Point[RouterConfigure.routerCount];
        lines = new Lines[LineNums];

        executorService=Executors.newSingleThreadExecutor();


    }


    @Override
    public void initView() {
        int screenHeight;
        int screenWidth;
        int frm_Height;
        int frm_Width;
        setTitle("OSPF模拟");

        setLayout(null);
        setBackGround();
        //隐藏标题栏
        // setUndecorated(true);
        Toolkit toolkit = getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        screenHeight = dimension.height;
        screenWidth = dimension.width;
        frm_Height = screenHeight;
        frm_Width = screenWidth;

        setSize(frm_Width, frm_Height);

        setLocation(screenWidth / 2 - frm_Width / 2, screenHeight / 2 - frm_Height / 2);

        circleButton = new CircleButton(50);
        circleButton.setBounds(screenWidth - 100, 20, 50, 50);

        add(circleButton);

        int D = 60;

        int xPadding = 300;
        int yPadding = 300;
        int yTransfer = 100;


        int firstLinStartX = (screenWidth - xPadding * 2 - D * 3) / 2;
        int firstLineStartY = (screenHeight - yPadding - D * 2) / 2 + yTransfer;

        routerViews[0] = new RouterView("1", CirclePosition.RIGHT);
        circleRouterPoints[0] = new Point(firstLinStartX, firstLineStartY);
        routerViews[0].setBounds(circleRouterPoints[0]);
        add(routerViews[0]);

        routerViews[1] = new RouterView("2", CirclePosition.BOTTOM);
        circleRouterPoints[1] = new Point(firstLinStartX + D + xPadding, firstLineStartY - yPadding);
        routerViews[1].setBounds(circleRouterPoints[1]);
        add(routerViews[1]);

        routerViews[2] = new RouterView("3", CirclePosition.LEFT);
        circleRouterPoints[2] = new Point(firstLinStartX + D * 2 + xPadding * 2, firstLineStartY);
        routerViews[2].setBounds(circleRouterPoints[2]);
        add(routerViews[2]);

        routerViews[3] = new RouterView("4", CirclePosition.TOP);
        circleRouterPoints[3] = new Point(firstLinStartX + xPadding / 2, firstLineStartY + D + yPadding);
        routerViews[3].setBounds(circleRouterPoints[3]);
        add(routerViews[3]);

        routerViews[4] = new RouterView("5", CirclePosition.TOP);
        circleRouterPoints[4] = new Point(firstLinStartX + D + xPadding + xPadding / 2, firstLineStartY + D + yPadding);
        routerViews[4].setBounds(circleRouterPoints[4]);
        add(routerViews[4]);

        lines[0] = new Lines(routerViews, 0, 2);
        lines[1] = new Lines(routerViews, 0, 3);
        lines[2] = new Lines(routerViews, 0, 4);
        lines[3] = new Lines(routerViews, 1, 2);

        for (int index = 0; index < LineNums; index++) {
            LittleRectXDistance[index] = lines[index].getPoint2().x - lines[index].getPoint1().x;
            LittleRectYDistance[index] = lines[index].getPoint2().y - lines[index].getPoint1().y;
        }
        for (int index = LineNums; index < LittleRectNums; index++) {
            LittleRectXDistance[index] = lines[index-LineNums].getPoint1().x - lines[index-LineNums].getPoint2().x;
            LittleRectYDistance[index] = lines[index-LineNums].getPoint1().y - lines[index-LineNums].getPoint2().y;
        }
        //设置窗体是否可以调整大小
        setResizable(true);

        setVisible(true);
        //关闭操作
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    private void drawFrame() {
        int times = 0;
        int MAXTIMES = 20;


        while (times < MAXTIMES) {
            for (int i = 0; i < LittleRectNums; i++) {
                LittleRectX[i] += LittleRectXDistance[i] / MAXTIMES;
                LittleRectY[i] += LittleRectYDistance[i] / MAXTIMES;
            }
            times++;

            if (times == MAXTIMES) {
                for (int i = 0; i < LittleRectNums; i++) {
                    LittleRectX[i] = 0;
                    LittleRectY[i] = 0;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    @Override
    public void initListener() {
        circleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                mainModel.sendMessage();
                executorService.execute(MainView.this::drawFrame);


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });
    }
}






