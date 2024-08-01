package br.com.avilamusic.music.service;

import br.com.avilamusic.music.model.Artista;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class ConsultaMyMemory {
    private static HttpClient client;

    public ConsultaMyMemory() {
        this.client = HttpClient.newHttpClient();
    }

    public static String obterInformacao(String nomeArtista) {
        String nomeCodificado = URLEncoder.encode(nomeArtista, StandardCharsets.UTF_8);
        String url = "https://api.mymemory.translated.net/get?q=" + nomeCodificado + "&langpair=en|pt-br";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao obter dados da API: " + e.getMessage(), e);
        }

        return response.body();
    }
}
