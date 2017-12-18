package com.travix.medusa.busyflights.controller;


import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.error.ErrorResponse;
import com.travix.medusa.busyflights.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest API Controller for Busy Flight
 *
 * @author silay
 */
@RestController
@RequestMapping(value = "/busyflight")
public class BusyFlightsController {

    private static Logger logger = LoggerFactory.getLogger(BusyFlightsController.class);

    @Autowired
    FlightService flightService;


    /**
     * REST Endpoint searching flights from suppliers
     *
     * @param busyFlightsRequest
     * @return List of flights
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<List<BusyFlightsResponse>> searchFlight(@Valid @RequestBody BusyFlightsRequest busyFlightsRequest) {
        logger.info("SearchFlight service requested");

        List<BusyFlightsResponse> response = flightService.searchFlight(busyFlightsRequest);

        logger.info("SearchFlight service completed with message");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {


        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(exception.getMessage());
        logger.info("Request is not valid " + errorMsg);
        return ErrorResponse.builder().message(errorMsg).build();
    }
}
