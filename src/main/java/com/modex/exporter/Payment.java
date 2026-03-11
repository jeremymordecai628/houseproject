package  com.modex.exporter; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public  class  Payment{
	private String serverUrl;
	private String phoneNumber; 
	private String amount; 
	private String accountReference;
	private String transactionDesc; 
	public Payment(String serverUrl, String phoneNumber, String amount, String accountReference, String transactionDesc){ 
		this.serverUrl = serverUrl;
		this.phoneNumber = phoneNumber; 
		this.amount = amount;
		this.accountReference = accountReference; 
		this.transactionDesc = transactionDesc; 
	}
	// Initiate payment (send request to server)
	public void initiatePayment(){
		try { 
			URL url = new URL(serverUrl + "/initiate-payment");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Content-Type", "application/json");
			Map<String, String> payload = new HashMap<>(); 
			payload.put("phoneNumber", phoneNumber); 
			payload.put("amount", amount);
			payload.put("accountReference", accountReference);
			payload.put("transactionDesc", transactionDesc);
			JSONObject jsonPayload = new JSONObject(payload);
			conn.setDoOutput(true); 
			conn.getOutputStream().write(jsonPayload.toString().getBytes());
			int responseCode = conn.getResponseCode();

			if (responseCode == 200)
			{ System.out.println("Payment initiated successfully");

			}else { 
				System.out.println("Error initiating payment: " + responseCode);
			} 
		} catch (Exception e) {
			System.out.println("Error initiating payment: " + e.getMessage());
		} }
	// Confirm payment (send request to server) 
	public void confirmPayment(String checkoutRequestID) { 
		try { 
			URL url = new URL(serverUrl + "/confirm-payment");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			Map<String, String> payload = new HashMap<>();
			payload.put("checkoutRequestID", checkoutRequestID); 
			JSONObject jsonPayload = new JSONObject(payload); 
			conn.setDoOutput(true); 

			conn.getOutputStream().write(jsonPayload.toString().getBytes());
			int responseCode = conn.getResponseCode(); 

			if (responseCode == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String response = reader.readLine(); 
				System.out.println("Payment confirmation response: " + response); 
			
			}else { 
				System.out.println("Error confirming payment: " + responseCode);
			}

		} catch (Exception e) { 
			System.out.println("Error confirming payment: " + e.getMessage()); 
		} 
	}
}
