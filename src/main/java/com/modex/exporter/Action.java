package com.modex.exporter;

public class Action {

    private String text;
    private String formart;
    private File  file;
    private String tokens;

    public static void setTokens(String value) {
        tokens = value;
    }

    public static void  sethumanize(String value){
        text= value;
    }
    public static void setconverter(String value , File file1){
        formart=value;
        file=file1;
    }

    public void checkTokenType() {
        if (tokens != null && tokens.startsWith("LLM")) {
            System.out.println("LLM");
        } else {
            System.out.println("COV");
        }
    }
    public String sendText() {
        try {
            URL url = new URL(serverUrl + "/humanize");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);  
            Map<String, String> payload = new HashMap<>();
            payload.put("text",text);
            payload.put("token",tokens);
            JSONObject jsonPayload = new JSONObject(payload);
            try (OutputStream output = conn.getOutputStream()) {
                output.write(jsonPayload.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            int responseCode = conn.getResponseCode();
            InputStream stream = (responseCode >= 200 && responseCode < 300)
               ? conn.getInputStream()
               : conn.getErrorStream();

           if (stream == null) {
               return "Error: Server returned HTTP " + responseCode;
           }
           StringBuilder response = new StringBuilder();
           try (BufferedReader reader = new BufferedReader(
                       new InputStreamReader(
                           stream,
                           java.nio.charset.StandardCharsets.UTF_8))) {
               String line;
               while ((line = reader.readLine()) != null) {
                   response.append(line);
               }}
           if (responseCode < 200 || responseCode >= 300) {
               return "Server error (" + responseCode + "): " + response;
           }
           JSONObject jsonResponse = new JSONObject(response.toString());

           // Change "text" to the actual key returned by your Flask API.
           return jsonResponse.getString("text");
        } catch (Exception e) {
            return "Request failed: " + e.getMessage();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    public byte[] sendFile() {
        HttpURLConnection conn = null;
        try {
            String boundary = "----JavaBoundary" + System.currentTimeMillis();
            URL url = new URL(serverUrl + "/process-file");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty(
                    "Content-Type", "multipart/form-data; boundary=" + boundary
                    );
            conn.setRequestProperty("Accept", "application/octet-stream");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(120000);
            conn.setDoOutput(true);
            try (OutputStream output = conn.getOutputStream();
                    PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(output, StandardCharsets.UTF_8),
                        true
                        )) 
            {
                // Send the authentication token.
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"token\"\r\n\r\n");
                writer.append(tokens).append("\r\n");

                //Send  file formart
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition:form-data; name=\"formart\"\r\n\r\n");
                writer.append(formart).append("r\n");

                // Send the file.
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                  .append(file.getName()).append("\"\r\n");
                writer.append("Content-Type: application/octet-stream\r\n\r\n");
                writer.flush();
                try (FileInputStream input = new FileInputStream(file)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                    output.flush();
                }
                writer.append("\r\n");
                writer.append("--").append(boundary).append("--\r\n");
                writer.flush();
            }
            int responseCode = conn.getResponseCode();

            InputStream responseStream =
                (responseCode >= 200 && responseCode < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();
            if (responseStream == null) {
                throw new IOException("Server returned HTTP " + responseCode);
            }
            byte[] response;
            try (InputStream input = responseStream;
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                byte[] data = new byte[8192];
                int bytesRead;
                while ((bytesRead = input.read(data)) != -1) {
                    buffer.write(data, 0, bytesRead);
                }
                response = buffer.toByteArray();
                    }
            if (responseCode < 200 || responseCode >= 300) {
                throw new IOException(
                        "Server error (" + responseCode + "): "
                        + new String(response, StandardCharsets.UTF_8)
                        );
            }
            return response;
        } catch (Exception e) {
            System.err.println("File transfer failed: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
