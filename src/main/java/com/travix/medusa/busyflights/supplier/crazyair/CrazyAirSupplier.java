package com.travix.medusa.busyflights.supplier.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
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
 * Crazy Air Supplier
 *
 * @author silay
 */
@Service
public class CrazyAirSupplier implements Supplier {

    private static Logger logger = LoggerFactory.getLogger(CrazyAirClient.class);


    @Autowired
    @Qualifier("crazyAirMapper")
    private MapperFacade mapperFacade;

    @Autowired
    private CrazyAirClient crazyAirClient;

    /**
     * Making rest api call to Crazy Air to search flights
     *
     * @param flightsInput
     * @return list of flights
     */
    @Override
    public List<FlightsOutput> searchFlight(FlightsInput flightsInput) {
        logger.info("Getting results from Crazy Air...");
        CrazyAirRequest crazyAirRequest = mapperFacade.map(flightsInput, CrazyAirRequest.class);
        CrazyAirResponse[] crazyAirResponses = crazyAirClient.searchFlight(crazyAirRequest);
        List<FlightsOutput> response = Arrays.stream(crazyAirResponses).map(crazyAirResponse -> mapperFacade.map(crazyAirResponse, FlightsOutput.class)).collect(Collectors.toList());
        logger.info("Got results from Crazy Air, number of flights : "+ response.size());
        return response;
    }

}
