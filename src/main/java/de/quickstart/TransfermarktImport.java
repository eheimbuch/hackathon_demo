package de.quickstart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class TransfermarktImport {

    private static final String BASE_URL = "http://localhost:8000";  // WICHTIG: ohne /docs
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Ergebnis inkl. Spieler-ID und Bild-URL
    public record TransferResult(long marketValue,
                                 int age,
                                 String nationality,
                                 String id,
                                 String imgurl) { }

    /**
     * 1) Spieler per Name suchen: /players/search/{name}
     *    -> marketValue, age, nationality, id
     * 2) Mit dieser id Profil holen: /players/{player_id}/profile
     *    -> imageUrl
     */
    public TransferResult getPlayerMarketValue(String name) {
        try {
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
            String url = BASE_URL + "/players/search/" + encodedName;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response =
                    HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "API-Fehler bei /players/search/{name}: HTTP "
                                + response.statusCode() + " – " + response.body());
            }

            JsonNode root = OBJECT_MAPPER.readTree(response.body());
            JsonNode results = root.path("results");

            if (!results.isArray() || results.isEmpty()) {
                return new TransferResult(0, 18, null, null, null);
            }

            JsonNode firstResult = results.get(0);

            // marketValue
            JsonNode marketValueNode = firstResult.path("marketValue");
            if (marketValueNode.isMissingNode() || !marketValueNode.isNumber()) {
                return new TransferResult(0, 18, null, null, null);
            }
            long marketValue = marketValueNode.asLong();

            // age
            int age = firstResult.path("age").asInt();

            // nationality (erste Nationalität)
            JsonNode nationalitiesNode = firstResult.path("nationalities");
            String firstNationality = null;
            if (nationalitiesNode.isArray() && nationalitiesNode.size() > 0) {
                firstNationality = nationalitiesNode.get(0).asText();
            }

            // Spieler-ID
            JsonNode idNode = firstResult.path("id");
            if (idNode.isMissingNode()) {
                throw new RuntimeException("id nicht in der Antwort gefunden: " + response.body());
            }
            String playerId = idNode.asText();

            // Bild-URL über zweite Funktion holen
            String imageUrl = getPlayerImageUrl(playerId);

            return new TransferResult(marketValue, age, firstNationality, playerId, imageUrl);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("HTTP-Request unterbrochen", e);
        } catch (IOException e) {
            throw new RuntimeException("Fehler bei HTTP-Request oder JSON-Verarbeitung", e);
        }
    }

    /**
     * Zweite Funktion:
     * Ruft /players/{player_id}/profile auf und extrahiert imageUrl.
     */
    public String getPlayerImageUrl(String playerId) {
        try {
            String url = BASE_URL + "/players/" + playerId + "/profile";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response =
                    HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "API-Fehler bei /players/{player_id}/profile: HTTP "
                                + response.statusCode() + " – " + response.body());
            }

            JsonNode root = OBJECT_MAPPER.readTree(response.body());

            JsonNode imageUrlNode = root.path("imageUrl");
            if (imageUrlNode.isMissingNode() || !imageUrlNode.isTextual()) {
                return "";
            }

            return imageUrlNode.asText();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("HTTP-Request unterbrochen", e);
        } catch (IOException e) {
            throw new RuntimeException("Fehler bei HTTP-Request oder JSON-Verarbeitung", e);
        }
    }
}
