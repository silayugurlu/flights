package com.travix.medusa.busyflights.service;


import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.supplier.crazyair.CrazyAirClient;
import com.travix.medusa.busyflights.supplier.crazyair.CrazyAirSupplier;
import com.travix.medusa.busyflights.supplier.toughjet.ToughJetClient;
import com.travix.medusa.busyflights.supplier.toughjet.ToughJetSupplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightServiceImplTest {

    @Autowired
    private FlightService flightService;

    @MockBean
    private CrazyAirSupplier crazyAirSupplier;

    @MockBean
    private ToughJetSupplier toughJetSupplier;

    @Before
    public void setUp() throws Exception {



        List<FlightsOutput> flightsOutputs1 = new ArrayList<>();
        flightsOutputs1.add(FlightsOutput.builder().

                amount(23).
                supplier("CrazyAir").
                departureAirportCode("LHR").
                destinationAirportCode("ALM").
                departureDate("2018-11-04").
                arrivalDate("2018-12-03").
                build());

        flightsOutputs1.add(FlightsOutput.builder().

                amount(24).
                supplier("CrazyAir").
                departureAirportCode("LHR").
                destinationAirportCode("ALM").
                departureDate("2018-11-04").
                arrivalDate("2018-11-05").
                build());
        List<FlightsOutput> flightsOutputs2 = new ArrayList<>();
        flightsOutputs2.add(FlightsOutput.builder().

                amount(28).
                supplier("ToughJet").
                departureAirportCode("LHR").
                destinationAirportCode("ALM").
                departureDate("2018-11-04").
                arrivalDate("2018-12-03").
                build());

        when(crazyAirSupplier.searchFlight(any(FlightsInput.class))).thenReturn(flightsOutputs1);
        when(toughJetSupplier.searchFlight(any(FlightsInput.class))).thenReturn(flightsOutputs2);
    }

    @Test
    public void searchFlightTest() {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-11-04").
                origin("LHR").
                destination("ALM").
                numberOfPassengers(3).
                returnDate("2018-12-03").
                build();
        List<BusyFlightsResponse> response = flightService.searchFlight(busyFlightsRequest);

        assertThat(response.size()).isEqualTo(3);
    }

}
