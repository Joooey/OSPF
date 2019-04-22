import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Client {
    // 默认连接到本机
    private String host = "localhost";
    private int port;
    private String name;

    public Client(int port,String name) {
        this.port = port;
        this.name = name;
    }


    // 连接到指定的主机和端口
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void sendMessageToTagetClient(int targetPort, String send) {
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

                out.writeUTF("客户端[" + name + "]:" + send);
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
        new Thread(){
            @Override
            public void run() {
                try {
                    // 建立服务器连接,设定客户连接请求队列的长度
                    ServerSocket server = new ServerSocket(port, 3);
                    while (true) {
                        // 等待客户连接
                        Socket socket = server.accept();
                        new Thread(new ServerThread(socket,port,name)).start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}


class ServerThread implements Runnable {
    private Socket socket;
    private int port;
    private String name;

    public ServerThread(Socket socket,int port,String name) {
        this.socket = socket;
        this.port = port;
        this.name=name;
    }

    @Override
    public void run() {
        System.out.print(name+"收到");
        try {
            try {
                // 读取信息的DataInputStream
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());
                // 发送信息的DataOutputStream
                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());
                // 读取来自客户端的信息
                String accpet = in.readUTF();
                System.out.println(accpet);
            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







