package com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi;

import com.animaladoption.petadoptionapi.config.DogApiConfig;
import com.animaladoption.petadoptionapi.domain.Animal;
import com.animaladoption.petadoptionapi.external.api.thatapicompany.dogapi.model.DogResultEvent;
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
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
@Slf4j
public class DogApiClient implements AnimalConnector {
    private final DogApiConfig dogApiConfig;
    private final HttpService httpService;
    private final JsonService jsonService;
    private final DogTransformer dogTransformer;

    private static final int PAGE_SIZE = 100;
    private static final String DOG_SEARCH_URL_FORMAT = "/v1/images/search?page=%d&limit=%d&order=ASC&has_breeds=true";

    public Response makeGetRequest(String path) {
        var request = new Request.Builder()
                .url(dogApiConfig.getUrl() + path)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", dogApiConfig.getApiKey())
                .build();

        try {
            return handleRateLimiting(() -> httpService.makeCall(request));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public Response handleRateLimiting(Supplier<Response> requestSupplier) throws InterruptedException {
        Response response;
        while (true) {
            response = requestSupplier.get();
            if (response.code() != 429) break;
            log.info("Rate limiting reached. Waiting 1 minute to try again");
            TimeUnit.SECONDS.sleep(61);
        }
        return response;
    }
    
    private List<DogResultEvent> getAllDogsFromProvider() {

        log.info("Retrieving all dogs from provider ");

        var page = 0;
        var result = new ArrayList<DogResultEvent>();

        while (true) {
            var response = makeGetRequest(String.format(DOG_SEARCH_URL_FORMAT, page, PAGE_SIZE));
            var body = httpService.getBodyFrom(response);
            var totalPages = response.headers("pagination-count");
            var currentPage = response.headers("pagination-page");


            var dogResultEvents = jsonService.fromJson(body, new TypeReference<List<DogResultEvent>>() {});

            log.debug("Retrieving dogs | Current page {} from {} pages", currentPage, totalPages);

            result.addAll(dogResultEvents);
            page++;

            if (dogResultEvents.isEmpty()) break;
        }

        log.info("Got total of {} dogs from provider", result.size());
        return result;
    }



    public List<Animal> getAllAnimals() {
        var dogsResultEventList = getAllDogsFromProvider();
        return dogTransformer.from(dogsResultEventList);
    }
}
