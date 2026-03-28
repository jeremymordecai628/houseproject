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

public  class  Payment{
	private String serverUrl;
	private String phoneNumber; 
	private String accountReference;
	private String clientId;
	private String WS_SERVER;
	private Session session;
	public Payment(String serverUrl, String phoneNumber, String accountReference, String clientId, String WS_SERVER){ 
		this.serverUrl = serverUrl;
		this.phoneNumber = phoneNumber; 
		this.accountReference = accountReference; 
		this.clientId = clientId;
		this.WS_SERVER = WS_SERVER;
	}
	// Connect WebSocket
	public void connect() {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			session = container.connectToServer(
					this,
					URI.create(WS_SERVER + clientId)
					);
			System.out.println("WebSocket connected");
		} catch (Exception e) {
			System.out.println("WebSocket error: " + e.getMessage());
		}
	}
	public void initiatePayment() {
		try {
			URL url = new URL(serverUrl + "/initiate-payment");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			Map<String, String> payload = new HashMap<>();
			payload.put("phone", phoneNumber);
			payload.put("clientId", clientId);
			payload.put("accountReference", accountReference);
			JSONObject jsonPayload = new JSONObject(payload);
			conn.getOutputStream().write(jsonPayload.toString().getBytes());
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				String response = reader.readLine();
				System.out.println("Server response: " + response);
			} else {
				System.out.println("Error: " + responseCode);
			}
		} catch (Exception e) {
			System.out.println("Error initiating payment: " + e.getMessage());
		}
	}
	// =========================
	//  WebSocket Callbacks
	// =========================
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected to server (WS)");
	}
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Payment update: " + message);
		if (message.contains("SUCCESS")) {
			System.out.println("✅ Payment Successful");
		} else if (message.contains("FAILED")) {
			System.out.println("❌ Payment Failed");
		}
	}
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		System.out.println("Disconnected: " + reason);
	}
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("Error: " + error.getMessage());
	}
}

