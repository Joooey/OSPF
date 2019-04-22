import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Client client1 = new Client(8080);
        Client client2 = new Client(8081);
        new Thread(new Runnable() {
            @Override
            public void run() {
                client1.receiveMessageFromOtherClient();
                client2.receiveMessageFromOtherClient();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                client2.sendMessageToTagetClient(8080,"message from client2");
                client1.sendMessageToTagetClient(8080,"message from client1");

            }
        }).start();




    }

}
