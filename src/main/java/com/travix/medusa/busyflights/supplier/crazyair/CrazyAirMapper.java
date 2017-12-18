package com.travix.medusa.busyflights.supplier.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.util.DateUtil;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Converts Crazy Air Objects
 *
 * @author silay
 */
@Component
public class CrazyAirMapper extends ConfigurableMapper {

    private static final String SUPPLIER = "CrazyAir";

    protected void configure(MapperFactory factory) {

        factory.classMap(FlightsInput.class, CrazyAirRequest.class)
                .field("numberOfPassengers", "passengerCount")
                .byDefault()
                .register();

        factory.classMap(CrazyAirResponse.class, FlightsOutput.class)
                .customize(new CustomMapper<CrazyAirResponse, FlightsOutput>() {
                    @Override
                    public void mapAtoB(CrazyAirResponse crazyAirResponse, FlightsOutput flightsOutput, MappingContext context) {
                        flightsOutput.setDepartureDate(DateUtil.formatDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME, DateTimeFormatter.ISO_DATE_TIME, crazyAirResponse.getDepartureDate()));
                        flightsOutput.setArrivalDate(DateUtil.formatDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME, DateTimeFormatter.ISO_DATE_TIME, crazyAirResponse.getArrivalDate()));
                        flightsOutput.setSupplier(SUPPLIER);
                        flightsOutput.setAmount(crazyAirResponse.getPrice());
                    }
                })
                .byDefault()
                .register();


    }
}
