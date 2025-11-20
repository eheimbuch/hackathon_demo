package de.quickstart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

@Service
public class TransfermarktImport {

    private static final String BASE_URL = "http://localhost:8000";  // WICHTIG: ohne /docs
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    
    // 2) PlayerID -> Marktwert (deine Funktion, leicht aufgeräumt)
     public long getPlayerMarketValue(String name) {

        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);

        String url = BASE_URL + "/players/search/" + encodedName;


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

         HttpResponse<String> response =
                 null;
         try {
             response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

         if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "API-Fehler bei /players/{id}/market_value: HTTP "
                            + response.statusCode() + " – " + response.body());
        }

         JsonNode root = null;
         try {
             root = OBJECT_MAPPER.readTree(response.body());
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         JsonNode results = root.path("results");
        
        if (!results.isArray() || results.isEmpty()) {
            throw new RuntimeException("Keine Ergebnisse gefunden: " + response.body());
        }
        
        JsonNode firstResult = results.get(0);
        JsonNode marketValueNode = firstResult.path("marketValue");
        
        if (marketValueNode.isMissingNode() || !marketValueNode.isNumber()) {
            throw new RuntimeException("marketValue nicht in der Antwort gefunden: " + response.body());
        }
        
        long value = marketValueNode.asLong();
        return value;
        
    }

}
