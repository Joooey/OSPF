import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Router {
    // 默认连接到本机
    private String host = "localhost";
    private int port;
    private String name;
    private RouteTable routeTable;

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

    public RouteTable getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    public void sendMessageToTagetClient(int targetPort, RouteTable routeTable) {
        try {
            // 连接到服务器
            Socket socket = new Socket(host, targetPort);

            try {
                // 读取服务器端传过来信息的DataInputStream
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());
                // 向服务器端发送信息的DataOutputStream
                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());

                Gson gson = new Gson();
                String jsonString = gson.toJson(routeTable);
                out.writeUTF(jsonString);
                // 读取来自服务器的信息
//                String accpet = in.readUTF();
//                System.out.println(accpet);

            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void receiveMessageFromOtherClient() {
        new Thread(() -> {
            try {
                // 建立服务器连接,设定客户连接请求队列的长度
                ServerSocket server = new ServerSocket(port, 3);
                while (true) {
                    // 等待客户连接
                    Socket socket = server.accept();
                    new Thread(new ServerThread(socket, port, name,routeTable)).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}


class ServerThread implements Runnable {
    private Socket socket;
    private int port;
    private String name;
    private RouteTable routeTable;

    public ServerThread(Socket socket, int port, String name,RouteTable routeTable) {
        this.socket = socket;
        this.port = port;
        this.name = name;
        this.routeTable=routeTable;
    }

    @Override
    public void run() {
        //System.out.print(name + "收到");
        try {
            try {
                // 读取信息的DataInputStream
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());
                // 发送信息的DataOutputStream
                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());
                // 读取来自客户端的信息
                String jsonString = in.readUTF();
                //System.out.println(jsonString);
                Gson gson = new Gson();
                RouteTable newrouteTable = gson.fromJson(jsonString, RouteTable.class);

                //更新本路由器路由表
                routeTable.updateRouteTable(newrouteTable);


            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







