package com.travix.medusa.busyflights.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FlightsInput {

    private String origin;
    private String destination;
    private String departureDate;
    private String returnDate;
    private int numberOfPassengers;
}
