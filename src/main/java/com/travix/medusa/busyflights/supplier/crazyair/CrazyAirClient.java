package com.travix.medusa.busyflights.supplier.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

@Service
public class CrazyAirClient {

    private static Logger logger = LoggerFactory.getLogger(CrazyAirClient.class);

    @Value("${crazyair.endpoint.url}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public CrazyAirClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public CrazyAirResponse[] searchFlight(CrazyAirRequest crazyAirRequest) {
        logger.info("Calling Crazy Air service, endpoint url: ", endpoint);

        ResponseEntity<CrazyAirResponse[]> result = restTemplate.exchange(endpoint, POST, RestUtil.getRequestEntity(crazyAirRequest), CrazyAirResponse[].class);

        if (result.getStatusCode() != OK) {
            logger.info("Could not get flights from Crazy Air");
            return new CrazyAirResponse[]{};
        }
        return result.getBody();

    }
}
