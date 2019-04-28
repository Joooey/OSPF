import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Joey
 * @date 2019/4/27
 */
public class ServerThread implements Runnable {
    private Socket socket;
    private int port;
    private String name;
    private RouteTable routeTable;




    public ServerThread(Socket socket, int port, String name, RouteTable routeTable) {
        this.socket = socket;
        this.port = port;
        this.name = name;
        this.routeTable = routeTable;
    }

    @Override
    public void run() {
        try {
            try {

                DataInputStream in = new DataInputStream(socket
                        .getInputStream());

                // 读取来自客户端的信息
                String jsonString = in.readUTF();
                //System.out.println(jsonString);
                Gson gson = new Gson();
                RouteTable newrouteTable = gson.fromJson(jsonString, RouteTable.class);

                //更新本路由器路由表
                routeTable.updateRouteTable(newrouteTable);
                routeTable.showRouteTable(name);
                //onShowMessageCallBack.showMessageCallBack();


            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}