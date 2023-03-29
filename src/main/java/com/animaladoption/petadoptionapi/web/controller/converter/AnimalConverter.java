package com.animaladoption.petadoptionapi.web.controller.converter;

import com.animaladoption.petadoptionapi.domain.AnimalCategory;
import com.animaladoption.petadoptionapi.domain.AnimalStatus;
import com.animaladoption.petadoptionapi.exception.ApiException;
import com.animaladoption.petadoptionapi.exception.ExceptionMessage;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsEvent;
import com.animaladoption.petadoptionapi.service.event.GetAnimalsResultEvent;
import com.animaladoption.petadoptionapi.web.controller.dto.AnimalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AnimalConverter {

    public AnimalStatus from (String status) {
        try {
            return AnimalStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ExceptionMessage.INVALID_STATUS);
        }
    }

    public GetAnimalsEvent from(Map<String, String> getAnimalRequestParam) {

        try {
            var term = getAnimalRequestParam.get("term");
            var category = Optional.ofNullable(getAnimalRequestParam.get("category")).map(AnimalCategory::valueOf).orElse(null);
            var status = Optional.ofNullable(getAnimalRequestParam.get("status")).map(AnimalStatus::valueOf).orElse(null);
            var createdAt = Optional.ofNullable(getAnimalRequestParam.get("createdAt")).map(LocalDateTime::parse).orElse(null);
            var pageIndex = Optional.ofNullable(getAnimalRequestParam.get("pageIndex")).map(Integer::valueOf).orElse(null);
            var pageSize = Optional.ofNullable(getAnimalRequestParam.get("pageSize")).map(Integer::valueOf).orElse(null);

            return GetAnimalsEvent.builder()
                    .term(term)
                    .category(category)
                    .status(status)
                    .createdAt(createdAt)
                    .pageIndex(pageIndex)
                    .pageSize(pageSize)
                    .build();
        } catch (Exception e) {
            log.error("Error while mapping getAnimalRequestParam {} to GetAnimalsEvent", getAnimalRequestParam, e);
            throw new RuntimeException();
        }

    }


    public HttpHeaders responseHeadersFrom(GetAnimalsResultEvent getAnimalsResultEvent) {
        var headers = new HttpHeaders();
        headers.add("x-total-pages", String.valueOf(getAnimalsResultEvent.getTotalPages()));
        headers.add("x-current-page", String.valueOf(getAnimalsResultEvent.getCurrentPage()));
        headers.add("x-next-page", String.valueOf(getAnimalsResultEvent.getNextPage()));

        return headers;
    }

    public List<AnimalResponse> from(GetAnimalsResultEvent getAnimalsResultEvent) {
        return getAnimalsResultEvent.getAnimalList()
                .stream()
                .map(animalResult ->
                        AnimalResponse.builder()
                                .id(animalResult.getId())
                                .name(animalResult.getName())
                                .description(animalResult.getDescription())
                                .imageUrl(animalResult.getImageUrl())
                                .category(animalResult.getCategory())
                                .createdAt(animalResult.getCreatedAt())
                                .status(animalResult.getStatus())
                                .build()
                )
                .toList();
    }
}
