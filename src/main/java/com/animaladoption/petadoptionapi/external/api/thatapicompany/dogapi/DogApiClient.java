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
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

@Service
@AllArgsConstructor
@Slf4j
public class DogApiClient implements AnimalConnector {
    private final DogApiConfig dogApiConfig;
    private final HttpService httpService;
    private final JsonService jsonService;
    private final DogTransformer dogTransformer;

    private Response makeGetRequest(String path) {

        var request = new Request.Builder()
                .url(dogApiConfig.getUrl() + path)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", dogApiConfig.getApiKey())
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

    private List<DogResultEvent> getAllDogsPaginated() {

        log.info("Retrieving all dogs from provider ");

        var page = 0;
        var result = new ArrayList<DogResultEvent>();

        while (true) {
            var url = String.format("/v1/images/search?page=%d&limit=100&order=ASC&has_breeds=true", page);
            var response = makeGetRequest(url);
            var body = httpService.getBodyFrom(response);
            var code = response.code();
            log.info("Status code {}", code);
            var dogs = jsonService.fromJson(body, new TypeReference<List<DogResultEvent>>() {});
            log.info("Amount of dogs in the page {} is {} | result size is {}", page, dogs.size(), result.size());
            result.addAll(dogs);

            var totalPages = parseInt(Objects.requireNonNull(response.header("pagination-count")));
            var current = parseInt(Objects.requireNonNull(response.header("pagination-page")));
            page = current + 1;


            log.info("Retrieving dogs | Page {} from {}", current, totalPages);
            if (current >= totalPages) break;
            else if (result.size()>150) break;
            if (dogs.isEmpty()) break;

//              break;
        }

        log.info("Got {} dogs from provider", result.size());

        return result;
    }



    public List<Animal> getAllAnimals() {
        var dogsResultEventList = getAllDogsPaginated();
        return dogTransformer.from(dogsResultEventList);
    }
}
