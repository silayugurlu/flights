package com.travix.medusa.busyflights.supplier.toughjet;


import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
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
public class ToughJetClient {

    private static Logger logger = LoggerFactory.getLogger(ToughJetClient.class);

    @Value("${toughjet.endpoint.url}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public ToughJetClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public ToughJetResponse[] searchFlight(ToughJetRequest toughJetRequest) {
        logger.info("Calling Tough Jet service, endpoint url: ", endpoint);
        ResponseEntity<ToughJetResponse[]> result = restTemplate.exchange(endpoint, POST, RestUtil.getRequestEntity(toughJetRequest), ToughJetResponse[].class);
        if (result.getStatusCode() != OK) {
            logger.info("Could not get flights from Tough Jet");
            return new ToughJetResponse[]{};
        }
        return result.getBody();
    }
}
