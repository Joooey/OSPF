import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Router {
    // 默认连接到本机
    private String host = "localhost";
    private int port;
    private String name;
    private int index;
    private RouteTable routeTable;
    private List<Router> AdjRouters = new ArrayList<>();
    private Distance[] distance;


    private OnUpdateViewListener onUpdateViewListener;



    public void setOnUpdateViewListener(OnUpdateViewListener onUpdateViewListener) {
        this.onUpdateViewListener = onUpdateViewListener;
        //onUpdateViewListener.updateView(this);
    }

    public void setDistance(Distance[] distance) {
        this.distance = distance;
    }

    public Distance[] getDistance() {
        return distance;
    }


    interface OnUpdateViewListener {
        void updateView(Router router);
    }

    public Router(int port, String name, int index) {
        this.port = port;
        this.name = name;
        this.index = index;

    }

    public int getIndex() {
        return index;
    }

    // 连接到指定的主机和端口
    public Router(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;

    }

    public void setAdjRouters(Router adjRouter) {
        AdjRouters.add(adjRouter);
    }

    public synchronized void sendMessageToTagetClient() {
        int targetPort;
        for (int index = 0; index < AdjRouters.size(); index++) {
            targetPort = AdjRouters.get(index).port;
            int finalTargetPort = targetPort;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        // 连接到服务器
                        Socket socket = new Socket(host, finalTargetPort);

                        try {
                            // 向服务器端发送信息的DataOutputStream
                            DataOutputStream out = new DataOutputStream(socket
                                    .getOutputStream());

                            Gson gson = new Gson();
                            try {
                                String jsonString = gson.toJson(routeTable);
                                out.writeUTF(jsonString);
                            } catch (Exception e) {
                                int a = 0;
                            }
                        } finally {
                            // socket.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }


    public synchronized void receiveMessageFromOtherClient() {
        new Thread(() -> {
            try {
                // 建立服务器连接,设定客户连接请求队列的长度
                ServerSocket server = new ServerSocket(port);
                while (true) {
                    // 等待客户连接
                    Socket socket = server.accept();

                    ServerThread serverThread = new ServerThread(socket, this, onUpdateViewListener);
                    serverThread.run();
                    // new Thread(serverThread).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}







