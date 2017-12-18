package com.travix.medusa.busyflights.supplier;

import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;

import java.util.List;

/**
 * Supplier for Flight Interface
 * @author silay
 */
public interface Supplier {

    /**
     * Search flights from supplier
     * @param flightsInput
     * @return flight list of suppliers
     */
    List<FlightsOutput> searchFlight(FlightsInput flightsInput);

}
