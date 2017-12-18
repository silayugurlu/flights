package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.busyflights.validator.date.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ValidDate
public class BusyFlightsRequest {

    @NotNull(message = "Origin can not be null")
    @Size(min = 3, max = 3, message = "Origin must be 3 letter IATA code(eg. LHR, AMS)")
    private String origin;

    @NotNull(message = "Destination can not be null")
    @Size(min = 3, max = 3, message = "Destination must be 3 letter IATA code(eg. LHR, AMS)")
    private String destination;

    @NotNull(message = "Departure Date can not be null")
    private String departureDate;

    @NotNull(message = "Return Date can not be null")
    private String returnDate;

    @NotNull
    @Min(value = 1, message = "Number of passengers must be at least 1")
    @Max(value = 4, message = "Number of passengers must be maximum 4")
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
