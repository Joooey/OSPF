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
    private Distance distance;

    private Router.OnUpdateViewListener onUpdateViewListener;


    public ServerThread(Socket socket, Router router, Router.OnUpdateViewListener onUpdateViewListener) {
        this.socket = socket;
        this.router = router;
        this.onUpdateViewListener = onUpdateViewListener;
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
                Gson gson = new Gson();
                RouteTable newrouteTable = gson.fromJson(receiveString, RouteTable.class);

//                System.out.println("接收到数据：" + receiveString);

                //更新本路由器路由表
                boolean b = router.getRouteTable().updateRouteTable(newrouteTable.getSelfname(), newrouteTable,router.getDistance());
                if (b) {
                    onUpdateViewListener.updateView(router);
                }


            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}