package com.travix.medusa.busyflights.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.error.ErrorResponse;
import com.travix.medusa.busyflights.service.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author silay
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BusyFlightsController.class)
public class BusyFlightsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Before
    public void setupMock() {

        BusyFlightsResponse busyFlightsResponse1 = BusyFlightsResponse.builder().
                airline("klm1").
                arrivalDate("2018-05-11").
                departureDate("2018-05-13").
                supplier("Tough").
                departureAirportCode("LHR").
                destinationAirportCode("AMS").
                fare("20.2").build();

        BusyFlightsResponse busyFlightsResponse2 = BusyFlightsResponse.builder().
                airline("klm2").
                arrivalDate("2018-05-11").
                departureDate("2018-05-13").
                supplier("Crazy").
                departureAirportCode("LHR").
                destinationAirportCode("AMS").
                fare("34.5").build();


        List<BusyFlightsResponse> responses = Arrays.asList(busyFlightsResponse1, busyFlightsResponse2);

        when(flightService.searchFlight(any(BusyFlightsRequest.class))).thenReturn(responses);


    }

    @Test
    public void searchFlightSuccessTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-12-03").
                returnDate("2018-12-07").
                destination("XYZ").
                origin("ABC").
                numberOfPassengers(3).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        BusyFlightsResponse[] result = mapper.readValue(mvcResult.getResponse().getContentAsString(), BusyFlightsResponse[].class);
        assertThat(result.length).isEqualTo(2);
        assertThat(result[0].getAirline()).isEqualTo("klm1");
    }

    @Test
    public void searchFlightInvalidDepartureDateTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2017-12-03").
                returnDate("2018-12-07").
                destination("XYZ").
                origin("ABC").
                numberOfPassengers(3).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Departure date is before current date");
    }

    @Test
    public void searchFlightInvalidReturnDateTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-02-03").
                returnDate("2018-01-01").
                destination("XYZ").
                origin("ABC").
                numberOfPassengers(3).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Return date is before departure date");
    }

    @Test
    public void searchFlightInvalidReturnDateFormatTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-02-03").
                returnDate("2018/01/01").
                destination("XYZ").
                origin("ABC").
                numberOfPassengers(3).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Return date is not in ISO_LOCAL_DATE format");
    }

    @Test
    public void searchFlightInvalidDepartureDateFormatTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018.02.03").
                returnDate("2018-01-01").
                destination("XYZ").
                origin("ABC").
                numberOfPassengers(3).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Departure date is not in ISO_LOCAL_DATE format");
    }

    @Test
    public void searchFlightInvalidNumOfPassengersTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-12-03").
                returnDate("2018-12-07").
                destination("XYZ").
                origin("ABC").
                numberOfPassengers(5).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Number of passengers must be maximum 4");
    }

    @Test
    public void searchFlightInvalidDestinationTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-12-03").
                returnDate("2018-12-07").
                destination("XYZL").
                origin("ABC").
                numberOfPassengers(2).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Destination must be 3 letter IATA code(eg. LHR, AMS)");
    }

    @Test
    public void searchFlightInvalidOriginTest() throws Exception {
        BusyFlightsRequest busyFlightsRequest = BusyFlightsRequest.builder().
                departureDate("2018-12-03").
                returnDate("2018-12-07").
                destination("XYZ").
                origin("AC").
                numberOfPassengers(2).build();

        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult = this.mockMvc.perform(post("/busyflight/search").content(mapper.writeValueAsBytes(busyFlightsRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        ErrorResponse result = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(result.getMessage()).isEqualTo("Origin must be 3 letter IATA code(eg. LHR, AMS)");
    }
}
