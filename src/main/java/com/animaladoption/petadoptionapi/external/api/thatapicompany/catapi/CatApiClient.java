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
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class CatApiClient implements AnimalConnector {
    private final CatApiConfig catApiConfig;
    private final HttpService httpService;
    private final JsonService jsonService;
    private final CatTransformer catTransformer;

    private static final int PAGE_SIZE = 100;
    private static final String CAT_SEARCH_URL_FORMAT = "/v1/images/search?page=%d&limit=%d&order=ASC&has_breeds=true";

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

    private List<CatResultEvent> getAllCatsFromProvider() {

        log.info("Retrieving all cats from provider ");

        var page = 0;
        var result = new ArrayList<CatResultEvent>();

        while (true) {
            var response = makeGetRequest(String.format(CAT_SEARCH_URL_FORMAT, page, PAGE_SIZE));
            var body = httpService.getBodyFrom(response);
            var totalPages = response.headers("pagination-count");
            var currentPage = response.headers("pagination-page");

            var catResultEventList = jsonService.fromJson(body, new TypeReference<List<CatResultEvent>>() {});

            log.debug("Retrieving cats | Current page {} from {} pages", currentPage, totalPages);

            result.addAll(catResultEventList);
            page++;

            if (catResultEventList.isEmpty()) break;
        }

        log.info("Got total of {} cats from provider", result.size());

        return result;
    }



    public List<Animal> getAllAnimals() {
        var catResultEventList = getAllCatsFromProvider();
        return catTransformer.from(catResultEventList);
    }
}
