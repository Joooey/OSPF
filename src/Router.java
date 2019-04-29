import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Router {
    // 默认连接到本机
    private String host = "localhost";
    private int port;
    private String name;
    private RouteTable routeTable;
    private List<Router> AdjRouters=new ArrayList<>();



    public Router(int port, String name) {
        this.port = port;
        this.name = name;
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

    public void sendMessageToTagetClient() {
        int targetPort;
        for(int index=0;index<AdjRouters.size();index++){
            targetPort=AdjRouters.get(index).port;

            try {
                // 连接到服务器
                Socket socket = new Socket(host, targetPort);

                try {
                    // 向服务器端发送信息的DataOutputStream
                    DataOutputStream out = new DataOutputStream(socket
                            .getOutputStream());

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(this.routeTable);
                    out.writeUTF(jsonString+"!"+name);

                } finally {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void receiveMessageFromOtherClient() {
        new Thread(() -> {
            try {
                // 建立服务器连接,设定客户连接请求队列的长度
                ServerSocket server = new ServerSocket(port);
                while (true) {
                    // 等待客户连接
                    Socket socket = server.accept();
                    ServerThread serverThread = new ServerThread(socket, this);
                    new Thread(serverThread).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}







