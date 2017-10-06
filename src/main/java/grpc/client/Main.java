package grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    //
    private static ParsingServiceClient client;

    public static void main(String[] args) {
        try{
            client = new ParsingServiceClient("127.0.0.1",3333);
            System.out.println("client: "+client);

            String data = "aaa\nbbb\nccc\nddd";
            for(int i=0; i<5; i++){
                List<String> list = client.parse(data);
                System.out.println("list: "+list);
                Thread.sleep(1000);
            }

        }catch(Throwable t){
            log.error(t.getMessage(),t);
        }
    }
}
