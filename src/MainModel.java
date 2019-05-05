import java.util.concurrent.*;

public class MainModel {
    private Router[] routers;

    private Executor executor;
    private BlockingDeque<Runnable> blockingDeque;

    public Router[] getRouters() {
        return routers;
    }


    private Router.OnUpdateViewListener onUpdateViewListener;




    public void sendMessage() {
        executor.execute(routers[0]::sendMessageToTagetClient);
        executor.execute(routers[1]::sendMessageToTagetClient);
        executor.execute(routers[2]::sendMessageToTagetClient);
        executor.execute(routers[3]::sendMessageToTagetClient);
        executor.execute(routers[4]::sendMessageToTagetClient);
    }


    public void showMessages() {
        for (int i = 0; i < 5; i++) {
            RouteTable routetable = routers[i].getRouteTable();
            routetable.showRouteTable(routers[i].getName());

        }

        System.out.println("--------------");
        System.out.println("");
    }

    public MainModel(Router.OnUpdateViewListener onUpdateViewListener) {
        this.onUpdateViewListener = onUpdateViewListener;
        initRouters();
        //showMessages();
        receiveMessages();
    }

    public void updateDistance(Distance[] distances ){
        for(int i=0;i<5;i++){
            routers[i].setDistance(distances);
        }
    }

    public void initRouters() {
        String targetrouter = "";
        String nextstep = "";
        int cost = 1;


        routers = new Router[5];

        routers[0] = new Router(8080, "0.0.0.1",0);
        routers[1] = new Router(8081, "0.0.0.2",1);
        routers[2] = new Router(8082, "0.0.0.3",2);
        routers[3] = new Router(8083, "0.0.0.4",3);
        routers[4] = new Router(8084, "0.0.0.5",4);


        InitAdjRouters(0, 2);
        InitAdjRouters(0, 3);
        InitAdjRouters(0, 4);
        InitAdjRouters(1, 2);

        for (int i = 0; i < 5; i++) {
            RouteTable routeTable = new RouteTable(routers[i].getName());
            routeTable.init(routers[i].getName());
            routers[i].setRouteTable(routeTable);
            routers[i].setOnUpdateViewListener(onUpdateViewListener);
        }




        blockingDeque = new LinkedBlockingDeque<>();

        executor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, blockingDeque);


    }

    public void InitAdjRouters(int Router1Index, int Router2Index) {
        routers[Router1Index].setAdjRouters(routers[Router2Index]);
        routers[Router2Index].setAdjRouters(routers[Router1Index]);

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
