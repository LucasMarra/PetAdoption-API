package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi;

import com.animaladoption.petadoptionapi.config.CatApiConfig;
import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi.model.CatResultEvent;
import com.animaladoption.petadoptionapi.service.AnimalConnector;
import com.animaladoption.petadoptionapi.service.HttpService;
import com.animaladoption.petadoptionapi.service.JsonService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;

@Service
@AllArgsConstructor
@Slf4j
public class CatApiClient implements AnimalConnector {
    private final CatApiConfig catApiConfig;
    private final HttpService httpService;
    private final JsonService jsonService;
    private final CatTransformer catTransformer;

    private Response makeGetRequest(String path) {

        var request = new Request.Builder()
                .url(catApiConfig.getUrl() + path)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", catApiConfig.getApiKey())
                .build();

        var response =  httpService.makeCall(request);
        if(response.code() == 429) {
            log.info("Rate limiting reached. Waiting 1 minute to try again");
            try {
                TimeUnit.SECONDS.sleep(61);
                return httpService.makeCall(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    private List<CatResultEvent> getAllCatsPaginated() {

        log.info("Retrieving all cats from provider ");

        var page = 0;
        var result = new ArrayList<CatResultEvent>();

        while (true) {
            var url = String.format("/v1/images/search?page=%d&limit=100&order=ASC&has_breeds=true", page);
            var response = makeGetRequest(url);
            var body = httpService.getBodyFrom(response);
            var code = response.code();
            log.info("Status code {}", code);
            var cats = jsonService.fromJson(body, new TypeReference<List<CatResultEvent>>() {});
            log.info("Amount of cats in the page {} is {} | result size is {}", page, cats.size(), result.size());
            result.addAll(cats);

            var totalPages = parseInt(Objects.requireNonNull(response.header("pagination-count")));
            var current = parseInt(Objects.requireNonNull(response.header("pagination-page")));
            page = current + 1;


            log.info("Retrieving cats | Page {} from {}", current, totalPages);
            if (current >= totalPages) break;
            if (cats.isEmpty()) break;
            else if (result.size()>150) break;
//              break;
        }

        log.info("Got {} cats from provider", result.size());

        return result;
    }



    public List<Animal> getAllAnimals() {
        var catResultEventList = getAllCatsPaginated();
        return catTransformer.from(catResultEventList);
    }
}
