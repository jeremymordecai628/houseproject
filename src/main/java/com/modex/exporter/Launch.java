package com.modex.exporter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.modex.dao.MetaDAO;
import com.modex.dao.TransactionDAO;
import com.modex.dao.ServicesDAO;
import com.modex.dao.ClientDataDAO;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Launch {

    public static void init() {
        MetaDAO metaDAO = new MetaDAO();
        ClientDataDAO client  = new ClientDataDAO();
	TransactionDAO trans = new TransactionDAO();
	ServicesDAO  service = new ServicesDAO();


        // Always ensure meta table exists
        metaDAO.createMetaTable();

        // Run only once
        if (!metaDAO.isInitialized()) {

            // Create all required tables
            client.createTable();
	    trans.createTable();
	    service.createTable();


            // Seed initial data
            studentDAO.insert(new Student("Admin"));

            // Mark as initialized
            metaDAO.setInitialized();
        }
    }
    public void initializedata() {
	    try {
		    String username = JOptionPane.showInputDialog(null,
				    "Enter your username login in our website", "Username Entry",
				    JOptionPane.PLAIN_MESSAGE);
		    String password = JOptionPane.showInputDialog(null, "Password used to login to the website", "PIN Entry",
				    JOptionPane.PLAIN_MESSAGE);
		    //Demo url 
		    String serverUrl = "https://modex.com/login";
		    URL url = new URL(serverUrl);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Content-Type", "application/json");
		    conn.setDoOutput(true);
		    Map<String, String> payload = new HashMap<>();
		    payload.put("username", username);
		    payload.put("password", password);
		    JSONObject jsonPayload = new JSONObject(payload);
		    conn.getOutputStream().write(jsonPayload.toString().getBytes());
		    int responseCode = conn.getResponseCode();
		    if (responseCode == 200) {
			    BufferedReader reader = new BufferedReader(
					    new InputStreamReader(conn.getInputStream()));
			    StringBuilder response = new StringBuilder();
			    String line;
			    while ((line = reader.readLine()) != null) {
				    response.append(line);
			    }
			    reader.close();
			    JSONObject json = new JSONObject(response.toString());
			    ClientDataDAO client = new ClientDataDAO (
					    json.getString("id"),
					    json.getString("name")
					    );
			    ServicesDAO service =new ServicesDAO(
					    json.getString("account_no");
					    json.getString("service_name");
					    );
			    // Example: store it
			    new ClientDataDAO().insert(client);
			    new ServicesDAO().insert(service);
		    } else {
			    System.out.println("Error: " + responseCode);
		    }
	    }}
}
