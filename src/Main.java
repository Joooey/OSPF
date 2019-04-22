

public class Main {

    public static void main(String[] args) {
        Client[] clients=new Client[5];
        clients[0]=new Client(8080,"Router1");
        clients[1] = new Client(8081,"Router2");
        clients[2] = new Client(8082,"Router3");
        clients[3] = new Client(8083,"Router4");
        clients[4] = new Client(8084,"Router5");

        for(int i=0;i<5;i++) {
            clients[i].receiveMessageFromOtherClient();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                clients[0].sendMessageToTagetClient(8080,"message from client0");
                clients[1].sendMessageToTagetClient(8080,"message from client1");
                clients[2].sendMessageToTagetClient(8080,"message from client2");
                clients[3].sendMessageToTagetClient(8080,"message from client3");
                clients[4].sendMessageToTagetClient(8080,"message from client4");

            }
        }).start();




    }

}
