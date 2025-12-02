package com.lisandro.gestorfinanzas.service.dolarapi;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lisandro.gestorfinanzas.dto.dolar.dolarData;

import org.springframework.cache.annotation.Cacheable;

@Service
public class DolarService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://dolarapi.com/v1/dolares";

    @Cacheable (value = "cotizaciones", key = "'todosLosDolares'")
    public List <dolarData> getAllDollars(){
        return restTemplate.exchange(
            API_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference <List<dolarData>>(){}
        ).getBody();
    }

}
