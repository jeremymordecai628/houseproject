package  com.modex.exporter; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import jakarta.websocket.*;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.CloseReason;
import jakarta.websocket.WebSocketContainer;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.modex.dao.TransactionDAO;

public  class  Payment{
	private String serverUrl; 
	private String accountReference;
	private String clientId;
	private Session session;
	public Payment(String serverUrl, String phoneNumber, String accountReference, String clientId, String WS_SERVER){ 
		this.serverUrl = serverUrl; 
		this.accountReference = accountReference; 
		this.clientId = clientId;
        this.session= session;
	}
    public static String sendPaymentDetails(String serverUrl) {

        try {
            String phone = JOptionPane.showInputDialog(
                null, "Enter phone number:"
            );

            if (phone == null || phone.trim().isEmpty()) {
                return "Phone number is required.";
            }


            JSONObject data = new JSONObject();
            data.put("phone", phone.trim());
            data.put("account_reference", accountReference.trim());

            HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(
                    data.toString(),
                    StandardCharsets.UTF_8
                ))
                .build();

            HttpResponse<String> response = client.send(
                request, BodyHandlers.ofString()
            );

            if (response.statusCode() >= 200 &&
                    response.statusCode() < 300) {
                JOptionPane.showMessageDialog(
                    null, "Server response:\n" + response.body()
                );
                return response.body();
            }

            return "Request failed. HTTP status: "
                + response.statusCode()
                + "\n" + response.body();

        } catch (Exception e) {
            return "Error sending details: " + e.getMessage();
        }
    }
    
    public static Transaction createTransaction(JSONObject json) {
        String transId = json.getString("trans_id");
        LocalDateTime paymentDate =
                LocalDateTime.parse(json.getString("paymentdate"));
        double amount = json.getDouble("amount");
        String paidId = json.getString("paid_id");

        return new Transaction(transId, paymentDate, amount, paidId);
    }
}

