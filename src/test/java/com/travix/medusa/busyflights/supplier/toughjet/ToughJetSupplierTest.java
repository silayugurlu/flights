package com.travix.medusa.busyflights.supplier.toughjet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToughJetSupplierTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToughJetSupplier toughJetSupplier;

    @MockBean
    ToughJetClient toughJetClient;

    @Before
    public void setUp() throws Exception {

        ToughJetResponse toughJetResponse1 = ToughJetResponse.builder().
                carrier("thy").
                inboundDateTime("2018-12-03T10:15:30Z").
                basePrice(15).
                tax(8).
                discount(0.2).
                departureAirportName("LHR").
                outboundDateTime("2018-12-03T14:15:30Z").
                arrivalAirportName("ALM").
                build();


        ToughJetResponse[] toughJetResponses = {toughJetResponse1};


        when(toughJetClient.searchFlight(any(ToughJetRequest.class))).thenReturn(toughJetResponses);
    }

    @Test
    public void searchFlightTest() {
        List<FlightsOutput> list = toughJetSupplier.searchFlight(FlightsInput.builder().
                                                                    departureDate("2018-11-03").
                                                                    destination("LHR").
                                                                    numberOfPassengers(3).
                                                                    origin("ALM").
                                                                    returnDate("2018-11-04").
                                                                    build());


        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getAirline()).isEqualTo("thy");
        assertThat(list.get(0).getAmount()).isEqualTo(22.97);
        assertThat(list.get(0).getArrivalDate()).isEqualTo(DateUtil.formatDateTime(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()), DateTimeFormatter.ISO_DATE_TIME, "2018-12-03T10:15:30Z"));
    }
}
