import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Joey
 * @date 2019/4/27
 */
public class ServerThread implements Runnable {
    private Socket socket;
    private Router router;


    public ServerThread(Socket socket, Router router) {
        this.socket = socket;
        this.router=router;
    }

    @Override
    public void run() {
        try {
            try {
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());
                String jsonString;
                String nameString;
                String receiveString = in.readUTF();
                String[] split = receiveString.split("!");
                jsonString=split[0];
                nameString=split[1];
                Gson gson = new Gson();
                RouteTable newrouteTable = gson.fromJson(jsonString, RouteTable.class);

                //更新本路由器路由表
                router.getRouteTable().updateRouteTable(nameString,newrouteTable);
                router.getRouteTable().showRouteTable(router.getName());


            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}