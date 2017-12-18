package com.travix.medusa.busyflights.supplier.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrazyAirSupplierTest {

    @Autowired
    private CrazyAirSupplier crazyAirSupplier;

    @MockBean
    private CrazyAirClient crazyAirClient;


    @Before
    public void setUp() throws Exception {

        CrazyAirResponse crazyAirResponse1 = CrazyAirResponse.builder().
                airline("thy").
                arrivalDate("2018-12-03T15:15:30").
                cabinclass("E").
                departureAirportCode("LHR").
                departureDate("2018-12-03T10:15:30").
                destinationAirportCode("ALM").
                price(12.3434).
                build();

        CrazyAirResponse crazyAirResponse2 = CrazyAirResponse.builder().
                airline("KLM").
                arrivalDate("2018-11-03T15:15:30").
                cabinclass("B").
                departureAirportCode("LHR").
                departureDate("2018-11-03T10:15:30").
                destinationAirportCode("ALM").
                price(15).
                build();

        CrazyAirResponse[] crazyAirResponses = {crazyAirResponse1, crazyAirResponse2};

        when(crazyAirClient.searchFlight(any(CrazyAirRequest.class))).thenReturn(crazyAirResponses);
    }

    @Test
    public void searchFlightTest() {
        List<FlightsOutput> list = crazyAirSupplier.searchFlight(FlightsInput.builder().
                                                                    departureDate("2018-11-03").
                                                                    destination("LHR").
                                                                    numberOfPassengers(3).
                                                                    origin("ALM").
                                                                    returnDate("2018-11-04").
                                                                    build());

        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getAmount()).isEqualTo(12.3434);
        assertThat(list.get(0).getArrivalDate()).isEqualTo("2018-12-03T15:15:30");
    }
}
