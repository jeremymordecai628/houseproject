package com.modex.exporter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * HumanizerService
 * ----------------
 * Handles communication between the desktop app and the local AI model.
 * Sends text to Ollama and returns a humanized version.
 */
public class HumanizerService {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public HumanizerService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    /**
     * Humanizes input text using a local LLM (Ollama).
     *
     * @param text Input text to rewrite
     * @return Humanized output text
     */
    public String humanize(String text) {
        try {
            if (text == null || text.isBlank()) {
                return "Please enter some text.";
            }

            String prompt = "Rewrite this text to sound natural, human, and conversational while keeping meaning:\n"
                    + text;

            String json = """
                    {
                      "model": "mistral",
                      "prompt": "%s",
                      "stream": false
                    }
                    """.formatted(escapeJson(prompt));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode node = mapper.readTree(response.body());
            return node.get("response").asText();

        } catch (Exception e) {
            return "Humanizer error: " + e.getMessage();
        }
    }

    /**
     * Escapes JSON special characters safely.
     */
    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}

