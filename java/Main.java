import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static class SendRequest implements Runnable {
        private String url;
     
        public SendRequest(String url) {
            this.url = url;
        }
     
        public String getUrl() {
            return url;
        }
     
        public void run() {
            try {
                URL url = new URL(this.url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                System.out.println("["+con.getResponseCode()+"] "+this.url);
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if(args.length < 2){
            throw new RuntimeException("\"Usage: java main <url1> <url2> .. <urln>\"");
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String url: args) {
            SendRequest task = new SendRequest("https://"+url);
            System.out.println("Created : " + task.getUrl());
 
            executor.execute(task);
        }
        executor.shutdown();
    }
}