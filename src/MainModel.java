import java.util.Scanner;

public class MainModel {

    private Router[] routers;


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
        Scanner scanner = new Scanner(System.in);

        String targetrouter = "abc";
        String nextstep;
        int cost;
        routers = new Router[5];

        routers[0] = new Router(8080, "a");
        routers[1] = new Router(8081, "b");
        routers[2] = new Router(8082, "c");
        routers[3] = new Router(8083, "d");
        routers[4] = new Router(8084, "e");


        for (int i = 0; i < 5; i++) {
            RouteTable routeTable = new RouteTable();
            routeTable.init(routers[i].getName());

            targetrouter = "abc";
            cost = i;
            nextstep = targetrouter;
            RouteRecord routeRecord = new RouteRecord(targetrouter, cost, nextstep);
            routeTable.updateRouteTable(routeRecord);
            routers[i].setRouteTable(routeTable);
        }

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
