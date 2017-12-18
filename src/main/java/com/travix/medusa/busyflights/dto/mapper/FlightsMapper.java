package com.travix.medusa.busyflights.dto.mapper;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.util.NumberUtil;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Converts objectss
 *
 * @author silay
 */
@Component
public class FlightsMapper extends ConfigurableMapper {

    /**
     * Converts BusyFlightsRequest to FlightsInput and FlightsOutput to BusyFlightsResponse
     *
     * @param factory
     */
    protected void configure(MapperFactory factory) {
        factory.classMap(FlightsInput.class, BusyFlightsRequest.class)
                .byDefault()
                .register();

        factory.classMap(FlightsOutput.class, BusyFlightsResponse.class)
                .customize(new CustomMapper<FlightsOutput,BusyFlightsResponse>() {
                    @Override
                    public void mapAtoB(FlightsOutput flightsOutput, BusyFlightsResponse busyFlightsResponse, MappingContext context) {
                        busyFlightsResponse.setFare(NumberUtil.formatFare(flightsOutput.getAmount()));
                    }
                })
                .byDefault()
                .register();

    }
}
