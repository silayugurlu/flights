package com.travix.medusa.busyflights.supplier.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.supplier.Supplier;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tough Jet Supplier
 *
 * @author silay
 */
@Service
public class ToughJetSupplier implements Supplier {

    private static Logger logger = LoggerFactory.getLogger(ToughJetSupplier.class);

    @Autowired
    @Qualifier("toughJetMapper")
    private MapperFacade mapperFacade;

    @Autowired
    private ToughJetClient toughJetClient;


    /**
     * Making rest api call to Tough Jet to search flights
     *
     * @param flightsInput
     * @return list of flights
     */
    @Override
    public List<FlightsOutput> searchFlight(FlightsInput flightsInput) {
        logger.info("Getting results from Tough Jet...");
        ToughJetRequest toughJetRequest = mapperFacade.map(flightsInput, ToughJetRequest.class);
        ToughJetResponse[] toughJetResponses = toughJetClient.searchFlight(toughJetRequest);
        List<FlightsOutput> response = Arrays.stream(toughJetResponses).map(toughJetResponse -> mapperFacade.map(toughJetResponse, FlightsOutput.class)).collect(Collectors.toList());
        logger.info("Got results from Tough Jet, number of flights : ", response.size());
        return response;
    }
}
