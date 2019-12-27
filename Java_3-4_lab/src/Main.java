import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String... args){
        BusinessCenter bc = new BusinessCenter();
        Random random = new Random();
        int numberOfClients = 5;
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfClients);
        for (int i = 0; i < numberOfClients; i++){
            new Thread(new Visitor(bc)).start();
            try {
                Thread.sleep(random.nextInt(300) % 100);
            }catch (InterruptedException e){

            }
        }
    }
}
