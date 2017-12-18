package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

/**
 * Flight Service
 *
 * @author silay
 */
public interface FlightService {

    /**
     * Searchs flights from suppliers
     *
     * @param busyFlightsRequests
     * @return list of flightss
     */
    List<BusyFlightsResponse> searchFlight(BusyFlightsRequest busyFlightsRequests);
}
