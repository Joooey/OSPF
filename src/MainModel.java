import java.util.Scanner;

public class MainModel {

    private Router[] routers;

    public Router[] getRouters() {
        return routers;
    }

    public void sendMessage() {
        new Thread(() -> {


            routers[0].sendMessageToTagetClient(8081, routers[0].getRouteTable());
            routers[1].sendMessageToTagetClient(8080, routers[1].getRouteTable());
            routers[2].sendMessageToTagetClient(8083, routers[2].getRouteTable());
            routers[3].sendMessageToTagetClient(8082, routers[3].getRouteTable());
            routers[4].sendMessageToTagetClient(8081, routers[4].getRouteTable());


        }).start();
    }

    public void showMessages() {
        for (int i = 0; i < 5; i++) {
            RouteTable routetable = routers[i].getRouteTable();
            routetable.showRouteTable(routers[i].getName());

        }
    }

    public MainModel() {
        initRouters();
        showMessages();
        receiveMessages();
    }

    public void initRouters() {
        String targetrouter="";
        String nextstep="";
        int cost=1;
        routers = new Router[5];

        routers[0] = new Router(8080, "0.0.0.1");
        routers[1] = new Router(8081, "0.0.0.2");
        routers[2] = new Router(8082, "0.0.0.3");
        routers[3] = new Router(8083, "0.0.0.4");
        routers[4] = new Router(8084, "0.0.0.5");


        for (int i = 0; i < 5; i++) {
            RouteTable routeTable = new RouteTable();
            routeTable.init(routers[i].getName());
            routers[i].setRouteTable(routeTable);
        }
        setInitRouteTable(0,2,1);
        setInitRouteTable(0,3,1);
        setInitRouteTable(0,4,1);
        setInitRouteTable(1,2,1);




    }
    public void setInitRouteTable(int routerFrom,int routerTo,int cost){
        RouteTable routeTable1=routers[routerFrom].getRouteTable();
        routeTable1.updateRouteTable(new RouteRecord(routers[routerTo].getName(),cost,routers[routerTo].getName()));
        routers[routerFrom].setRouteTable(routeTable1);

        RouteTable routeTable2=routers[routerTo].getRouteTable();
        routeTable2.updateRouteTable(new RouteRecord(routers[routerFrom].getName(),cost,routers[routerFrom].getName()));
        routers[routerTo].setRouteTable(routeTable2);
    }

    /**
     * 每个路由器开启一个等待连接接收消息的线程
     */
    public void receiveMessages() {

        for (int i = 0; i < 5; i++) {
            routers[i].receiveMessageFromOtherClient();
        }

    }


}
