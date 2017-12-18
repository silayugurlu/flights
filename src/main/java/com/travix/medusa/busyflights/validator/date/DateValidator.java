package com.travix.medusa.busyflights.validator.date;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.util.DateUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;

/**
 * Validates date values
 *
 * @author silay
 */
public class DateValidator implements ConstraintValidator<ValidDate, Object> {

    @Override
    public void initialize(ValidDate validDate) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BusyFlightsRequest request = (BusyFlightsRequest) value;

        String departureDate = request.getDepartureDate();
        String returnDate = request.getReturnDate();

        //TODO validation messages should be localized

        /**
         * Checks if departureDate is in ISO_LOCAL_DATE format
         */
        if(!DateUtil.isValidFormat(DateTimeFormatter.ISO_LOCAL_DATE, departureDate) ){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Departure date is not in ISO_LOCAL_DATE format")
                    .addPropertyNode("departureDate").addConstraintViolation();
            return false;
        }

        /**
         * Checks if returnDate is in ISO_LOCAL_DATE format
         */
        if(!DateUtil.isValidFormat(DateTimeFormatter.ISO_LOCAL_DATE, returnDate)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Return date is not in ISO_LOCAL_DATE format")
                    .addPropertyNode("returnDate").addConstraintViolation();
            return false;
        }

        /**
         * Checks if departure date is after current date
         */
        if(!DateUtil.isAfterOrEqualNow(DateTimeFormatter.ISO_LOCAL_DATE, departureDate)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Departure date is before current date")
                    .addPropertyNode("departureDate").addConstraintViolation();
            return false;
        }

        /**
         * Checks if return date is after departure date
         */
        if(!DateUtil.isBeforeOrEqual(DateTimeFormatter.ISO_LOCAL_DATE, departureDate, DateTimeFormatter.ISO_LOCAL_DATE, returnDate)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Return date is before departure date")
                    .addPropertyNode("returnDate").addConstraintViolation();
            return false;
        }

        return true;

    }

}
