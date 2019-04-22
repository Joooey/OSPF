import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 8080;// 默认服务器端口

    public Server() {
    }

    // 创建指定端口的服务器
    public Server(int port) {
        this.port = port;
    }

    // 提供服务
    public void service() {
        int i = 0;
        try {
            // 建立服务器连接,设定客户连接请求队列的长度
            ServerSocket server = new ServerSocket(port, 3);
            while (true) {
                // 等待客户连接
                Socket socket = server.accept();
                i++;
                System.out.println("第" + i + "个客户连接成功！");
                new Thread(new ServerThread(socket, i)).run();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().service();
    }
}

//
//class ServerThread implements Runnable {
//    private int index;
//    private Socket socket;
//
//    public ServerThread(Socket socket, int i) {
//        this.socket = socket;
//        this.index = i;
//    }
//
//    // 任务是为一个用户提供服务
//    @Override
//    public void run() {
//        try {
//            try {
//                // 读取信息的DataInputStream
//                DataInputStream in = new DataInputStream(socket
//                        .getInputStream());
//                // 发送信息的DataOutputStream
//                DataOutputStream out = new DataOutputStream(socket
//                        .getOutputStream());
//                while (true) {
//                    // 读取来自客户端的信息
//                    String accpet = in.readUTF();
//                    System.out.println("第" + index + "个客户端发出消息：" + accpet);
//                }
//            } finally {// 建立连接失败的话不会执行socket.close();
//                socket.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
