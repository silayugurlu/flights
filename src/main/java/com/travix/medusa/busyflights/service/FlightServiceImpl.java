package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.supplier.Supplier;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Flight Service Implementation
 *
 * @author silay
 */
@Service
public class FlightServiceImpl implements FlightService, ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Autowired
    @Qualifier("flightsMapper")
    MapperFacade mapperFacade;


    private Optional<List<Supplier>> suppliers;

    @Override
    public List<BusyFlightsResponse> searchFlight(BusyFlightsRequest busyFlightsRequest) {

        logger.info("Converting Busy Flight Request to Flight Input");
        /**
         * Converts BusyFlightsRequest object to FlightsInput object
         */
        FlightsInput flightsInput = mapperFacade.map(busyFlightsRequest, FlightsInput.class);

        logger.info("Calling all suppliers search flight api");
        /**
         * Calls searchFlight and merge results for all suppliers that are loaded on contextRefreshed
         */

        List<FlightsOutput> flightsOutputs = suppliers
                .map(f ->
                        f.stream().parallel()
                                .map(supplier ->
                                        supplier.searchFlight(flightsInput))
                                .flatMap(Collection::stream).sorted(Comparator.comparingDouble(FlightsOutput::getAmount))
                                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        logger.info("Data collected from all suppliers, converting to Busy Flight Response");
        /**
         * Converts list of FlightOutput to list BusyFlightOutput list
         */
        return mapperFacade.mapAsList(flightsOutputs, BusyFlightsResponse.class);
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        logger.info("ContextRefreshed, Suppliers loaded");
        /**
         *  Called when application is initialized or refreshed and loads all Supplier beans
         */
        suppliers = Optional.of(new ArrayList<>(contextRefreshedEvent.getApplicationContext().getBeansOfType(Supplier.class).values()));

    }
}
