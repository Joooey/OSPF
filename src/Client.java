import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class Client {
    private String host = "localhost";// 默认连接到本机
    private int port;// 默认连接到端口8080

    private String name = String.valueOf(new Random().nextInt(999999));//客户端的名字

    public Client(int port) {
        this.port = port;
    }


    // 连接到指定的主机和端口
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessageToTagetClient(int targetPort) {
        this.sendMessageToTagetClient(host, targetPort);
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

                // 装饰标准输入流，用于从控制台输入

                // 把从控制台得到的信息传送给服务器
                System.out.println(send);
                out.writeUTF("客户端[" + name + "]:" + send);
                // 读取来自服务器的信息
                String accpet = in.readUTF();
                System.out.println(accpet);

            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToTagetClient(String targetHost, int targetPort) {
        try {
            // 连接到服务器
            Socket socket = new Socket(targetHost, targetPort);

            try {
                // 读取服务器端传过来信息的DataInputStream
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());
                // 向服务器端发送信息的DataOutputStream
                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());

                // 装饰标准输入流，用于从控制台输入
                Scanner scanner = new Scanner(System.in);

                String send = scanner.nextLine();
                // 把从控制台得到的信息传送给服务器
                System.out.println(send);
                out.writeUTF("客户端[" + name + "]:" + send);
                // 读取来自服务器的信息
                String accpet = in.readUTF();
                System.out.println(accpet);

            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessageFromOtherClient() {
        int i = 0;
        try {
            // 建立服务器连接,设定客户连接请求队列的长度
            ServerSocket server = new ServerSocket(port, 3);
            while (true) {
                // 等待客户连接
                Socket socket = server.accept();
                i++;
                System.out.println("第" + i + "个客户连接成功！");
                new Thread(new ServerThread(socket, i)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ServerThread implements Runnable {
    private int index;
    private Socket socket;

    public ServerThread(Socket socket, int i) {
        this.socket = socket;
        this.index = i;
    }

    // 任务是为一个用户提供服务
    @Override
    public void run() {
        try {
            try {
                // 读取信息的DataInputStream
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());
                // 发送信息的DataOutputStream
                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());
                while (true) {
                    // 读取来自客户端的信息
                    String accpet = in.readUTF();
                    System.out.println("第" + index + "个客户端发出消息：" + accpet);
                }
            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







