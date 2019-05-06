import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainView extends JFrame implements IMainView, Router.OnUpdateViewListener {


    private CircleButton circleButton;
    private JTextField textField;
    private RouterView[] routerViews;
    private JTextField[] textFields;
    private JButton submitButton;
    private Point[] circleRouterPoints;
    private Lines[] lines;
    private Router[] routers;
    private int[] dd;
    private int LittleRectNums = 8;
    private int LineNums = 4;
    private int LittleRectWidth = 10;
    private int LittleRectX[] = new int[LittleRectNums];
    private int LittleRectY[] = new int[LittleRectNums];
    private int LittleRectXDistance[] = new int[LittleRectNums];
    private int LittleRectYDistance[] = new int[LittleRectNums];

    private ExecutorService executorService;

    private MainModel mainModel;


    public MainView() throws HeadlessException {
        init();
    }

    private void init() {
        initData();
        initView();
        initListener();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.ORANGE);

        for (int index = LineNums; index < LittleRectNums; index++) {
            g.fillRect(LittleRectX[index] + lines[index - LineNums].getPoint2().x, LittleRectY[index] + lines[index - LineNums].getPoint2().y, LittleRectWidth, LittleRectWidth);
        }

        g.setColor(ViewConfigure.defaultTextColor);
        for (int index = 0; index < LineNums; index++) {
            g.drawLine(lines[index].getPoint1().x, lines[index].getPoint1().y, lines[index].getPoint2().x, lines[index].getPoint2().y);
            g.fillRect(LittleRectX[index] + lines[index].getPoint1().x, LittleRectY[index] + lines[index].getPoint1().y, LittleRectWidth, LittleRectWidth);

        }
        for (int index = 0; index < LineNums; index++) {
            g.setFont(ViewConfigure.defaultTextFont);
            g.drawString(String.valueOf(dd[index]), (lines[index].getPoint1().x + lines[index].getPoint2().x) / 2, (lines[index].getPoint1().y + lines[index].getPoint2().y) / 2);

        }


    }

    public void setBackGround() {
        ((JPanel) this.getContentPane()).setOpaque(false);
        ImageIcon img = new ImageIcon("res\\background.png");
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
    }


    @Override
    public void initData() {
        mainModel = new MainModel(this::updateView);
        routerViews = new RouterView[RouterConfigure.routerCount];
        circleRouterPoints = new Point[RouterConfigure.routerCount];
        lines = new Lines[LineNums];
        dd = new int[4];
        for (int i = 0; i < 4; i++) {
            dd[i] = 1;
        }
        executorService = Executors.newSingleThreadExecutor();


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

        circleButton = new CircleButton(50, "SEND");
        circleButton.setBounds(screenWidth - 100, 20, 50, 50);

        add(circleButton);

        int D = 60;

        int xPadding = getWidth()/5;
        int yPadding = getHeight()/4;
        int yTransfer = -50;


        int firstLinStartX = 50;
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

        //布局距离输入

        JLabel label = new JLabel("设置距离：");
        label.setForeground(ViewConfigure.defaultTextColor);
        label.setFont(ViewConfigure.defaultTextFont);
        label.setBounds(screenWidth - getWidth()/4, 100, 100, 50);
        add(label);
        JLabel label1 = new JLabel("Router 1 - Router 3 :");
        label1.setForeground(ViewConfigure.defaultTextColor);
        label1.setFont(ViewConfigure.defaultTextFont);
        label1.setBounds(screenWidth - getWidth()/4, 200, 300, 50);
        add(label1);
        JLabel label2 = new JLabel("Router 1 - Router 4 :");
        label2.setForeground(ViewConfigure.defaultTextColor);
        label2.setFont(ViewConfigure.defaultTextFont);
        label2.setBounds(screenWidth - getWidth()/4, 300, 300, 50);
        add(label2);
        JLabel label3 = new JLabel("Router 1 - Router 5 :");
        label3.setForeground(ViewConfigure.defaultTextColor);
        label3.setFont(ViewConfigure.defaultTextFont);
        label3.setBounds(screenWidth - getWidth()/4, 400, 300, 50);
        add(label3);

        JLabel label4 = new JLabel("Router 2 - Router 3 ");
        label4.setForeground(ViewConfigure.defaultTextColor);
        label4.setFont(ViewConfigure.defaultTextFont);
        label4.setBounds(screenWidth - getWidth()/4, 500, 300, 50);
        add(label4);


        textFields = new JTextField[4];

        textFields[0] = new JTextField(10);
        textFields[0].setText(String.valueOf(1));
        textFields[0].setBounds(screenWidth - getWidth()/8, 210, 60, 30);
        add(textFields[0]);

        textFields[1] = new JTextField(10);
        textFields[1].setText(String.valueOf(1));
        textFields[1].setBounds(screenWidth - getWidth()/8, 310, 60, 30);
        add(textFields[1]);

        textFields[2] = new JTextField(10);
        textFields[2].setText(String.valueOf(1));
        textFields[2].setBounds(screenWidth - getWidth()/8, 410, 60, 30);
        add(textFields[2]);

        textFields[3] = new JTextField(10);
        textFields[3].setText(String.valueOf(1));
        textFields[3].setBounds(screenWidth - getWidth()/8, 510, 60, 30);
        add(textFields[3]);

        submitButton = new JButton("重置");
        submitButton.setBounds(screenWidth - getWidth()/5, 610, 60, 30);
        add(submitButton);


        lines[0] = new Lines(routerViews, 0, 2);
        lines[1] = new Lines(routerViews, 0, 3);
        lines[2] = new Lines(routerViews, 0, 4);
        lines[3] = new Lines(routerViews, 1, 2);
        ComputeDistance();
        routers = mainModel.getRouters();
        for (int i = 0; i < 5; i++) {
            updateView(routers[i]);
        }

        //设置窗体是否可以调整大小
        setResizable(true);

        setVisible(true);
        //关闭操作
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * 计算动画小方块x,y方向移动的距离
     */
    private void ComputeDistance() {

        for (int index = 0; index < LineNums; index++) {
            LittleRectXDistance[index] = lines[index].getPoint2().x - lines[index].getPoint1().x;
            LittleRectYDistance[index] = lines[index].getPoint2().y - lines[index].getPoint1().y;
        }
        for (int index = LineNums; index < LittleRectNums; index++) {
            LittleRectXDistance[index] = lines[index - LineNums].getPoint1().x - lines[index - LineNums].getPoint2().x;
            LittleRectYDistance[index] = lines[index - LineNums].getPoint1().y - lines[index - LineNums].getPoint2().y;
        }
    }

    private void addRouteRecord(int i) {
        String target = "";
        String next = "";
        int cost = 0;
        String resultString = "";
        RouteTable routeTable = mainModel.getRouters()[i].getRouteTable();

        Vector<Moment> moments = new Vector<>();
        moments.add(new Moment("      目的地址        费用      下一跳"));
        for (RouteRecord routeRecord : routeTable.getRoutetable()) {
            target = routeRecord.getTargetRouter();
            next = routeRecord.getNextStep();
            cost = routeRecord.getCost();
            resultString = "       " + target + "           " + cost + "           " + next;
            moments.add(new Moment(resultString));

        }
        routerViews[i].replaceMoments(moments);
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
                updateDistanceFromUi();
                executorService.execute(MainView.this::drawFrame);
                mainModel.sendMessage();
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

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frameInit();
                for (Router router:mainModel.getRouters()){
                    router.freeServer();
                }
                init();
                updateDistanceFromUi();
            }
        });


    }

    private void updateDistanceFromUi() {
        Router[] routers = mainModel.getRouters();
        Distance distance[] = new Distance[4];
        String s0 = textFields[0].getText();
        if (!"".equals(s0)) {
            int d0 = Integer.parseInt(s0);
            dd[0] = d0;
            distance[0] = new Distance(routers[0], routers[2], d0);
        }

        String s1 = textFields[1].getText();
        if (!"".equals(s1)) {
            int d1 = Integer.parseInt(s1);
            dd[1] = d1;
            distance[1] = new Distance(routers[0], routers[3], d1);
        }
        String s2 = textFields[2].getText();
        if (!"".equals(s2)) {
            int d2 = Integer.parseInt(s2);
            dd[2] = d2;
            distance[2] = new Distance(routers[0], routers[4], d2);
        }
        String s3 = textFields[3].getText();
        if (!"".equals(s3)) {
            int d3 = Integer.parseInt(s3);
            dd[3] = d3;
            distance[3] = new Distance(routers[1], routers[2], d3);
        }

        mainModel.updateDistance(distance);
    }

    @Override
    public void updateView(Router router) {

        addRouteRecord(router.getIndex());
    }
}






