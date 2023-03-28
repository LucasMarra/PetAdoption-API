package com.animaladoption.petadoptionapi.external.api.thatapicompany.catapi;

import com.animaladoption.petadoptionapi.config.CatApiConfig;
import com.animaladoption.petadoptionapi.service.HttpService;
import com.animaladoption.petadoptionapi.service.JsonService;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CatApiClientTest {

    private CatApiClient catApiClient;

    @Mock private CatApiConfig catApiConfig;
    @Mock private HttpService httpService;
    @Mock private JsonService jsonService;
    @Mock private CatTransformer catTransformer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        catApiClient = new CatApiClient(catApiConfig, httpService, jsonService, catTransformer);
    }

    @Test
    @DisplayName("handleRateLimiting() should return response when response code is not 429")
    void catApiClientHandleTooManyRequestsTest01() throws InterruptedException {
        // Arrange
        Response response = new Response.Builder()
                .request(new Request.Builder().url("http://example.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("", MediaType.get("application/json")))
                .build();
        Supplier<Response> requestSupplier = mock(Supplier.class);
        when(requestSupplier.get()).thenReturn(response);

        // Act
        Response result = catApiClient.handleRateLimiting(requestSupplier);

        // Assert
        assertEquals(response, result);
    }

    @Test
    @DisplayName("handleRateLimiting() should return response when response code is 429 after one retry")
    void catApiClientHandleTooManyRequestsTest02() throws InterruptedException {
        // Arrange
        Response response429 = new Response.Builder()
                .request(new Request.Builder().url("http://example.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(429)
                .message("Too Many Requests")
                .body(ResponseBody.create("", MediaType.get("application/json")))
                .build();
        Response response200 = new Response.Builder()
                .request(new Request.Builder().url("http://example.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("", MediaType.get("application/json")))
                .build();
        Supplier<Response> requestSupplier = mock(Supplier.class);
        when(requestSupplier.get()).thenReturn(response429, response200);

        // Act
        Response result = catApiClient.handleRateLimiting(requestSupplier);

        // Assert
        assertEquals(response200, result);
    }

}