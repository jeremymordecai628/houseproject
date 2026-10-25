package com.modex.exporter;

import io.socket.client.Socket;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.function.Consumer;

public class SocketManager {

    private Socket socket;

    /*
     * Initializes the Socket.IO connection and registers general listeners.
     **/
    public void connect(String serverUrl) {
        try {
            socket = IO.socket(serverUrl);

            socket.on(Socket.EVENT_CONNECT, args ->
                System.out.println("Connected to Flask server.")
            );

            socket.on(Socket.EVENT_DISCONNECT, args ->
                System.out.println("Disconnected from Flask server.")
            );

            socket.on(Socket.EVENT_CONNECT_ERROR, args ->
                System.err.println("Connection error: " + args[0])
            );


            socket.connect();

        } catch (URISyntaxException e) {
            System.err.println("Invalid server URL: " + e.getMessage());
        }
    }

    /*
     * Sends data to the server using any event name.
     **/
    public void send(String event, JSONObject data) {
        if (socket != null && socket.connected()) {
            socket.emit(event, data);
        } else {
            System.err.println("Cannot send: socket is not connected.");
        }
    }

    /*
     * Registers a listener for any event received from the server.
     **/
    public void on(String event, Consumer<Object[]> callback) {
        if (socket != null) {
            socket.on(event, args -> callback.accept(args));
        }
    }

    /*
     * Checks whether the socket is currently connected.
     **/
    public boolean isConnected() {
        return socket != null && socket.connected();
    }

    /*
     * Disconnects and releases the socket resources.
     **/
    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
            socket.close();
            socket = null;
        }
    }
}

