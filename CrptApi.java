import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CrptApi {
    private Semaphore semaphore;
    private int requestsLimit;
    private long interval;

    public CrptApi(int requestsLimit, long interval) {
        this.requestsLimit = requestsLimit;
        this.interval = interval;
        this.semaphore = new Semaphore(requestsLimit);
    }

    public void createDocument(Object document, String signature) {
        try {
            semaphore.acquire();


            Thread.sleep(1000);

          
            System.out.println("Document: " + document.toString());
            System.out.println("Signature: " + signature);

           
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CrptApi api = new CrptApi(5, 1000);

        for (int i = 0; i < 10; i++) {
            final int documentNumber = i + 1;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    api.createDocument("Document " + documentNumber, "Signature " + documentNumber);
                }
            });
            thread.start();
        }
    }
}
