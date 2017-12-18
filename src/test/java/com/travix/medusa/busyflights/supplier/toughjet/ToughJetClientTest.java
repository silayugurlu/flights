package com.travix.medusa.busyflights.supplier.toughjet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.supplier.crazyair.CrazyAirClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(ToughJetClient.class)
public class ToughJetClientTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ToughJetClient toughJetClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Before
    public void setUp() throws Exception {

        ToughJetResponse toughJetResponse1 = ToughJetResponse.builder().
                carrier("thy").
                inboundDateTime("2018-12-03T15:15:30").
                basePrice(15).
                tax(8).
                discount(0.2).
                departureAirportName("LHR").
                outboundDateTime("2018-12-03T10:15:30").
                arrivalAirportName("ALM").
                build();

        ToughJetResponse toughJetResponse2 = ToughJetResponse.builder().
                carrier("thy").
                inboundDateTime("2018-12-03T15:15:30").
                basePrice(15).
                tax(8).
                discount(0.2).
                departureAirportName("LHR").
                outboundDateTime("2018-12-03T10:15:30").
                arrivalAirportName("ALM").
                build();

        ToughJetResponse[] toughJetResponses = {toughJetResponse1, toughJetResponse2};
        String detailsString =
                objectMapper.writeValueAsString(toughJetResponses);

        this.server.expect(requestTo("/toughjet"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    }

    @Test
    public void searchFlightTest() {
        ToughJetResponse[] response = toughJetClient.searchFlight(ToughJetRequest.builder().
                                                                    outboundDate("2018-11-03").
                                                                    to("LHR").
                                                                    numberOfAdults(3).
                                                                    from("ALM").
                                                                    inboundDate("2018-11-04").
                                                                    build());

        assertThat(response.length).isEqualTo(2);
    }
}
