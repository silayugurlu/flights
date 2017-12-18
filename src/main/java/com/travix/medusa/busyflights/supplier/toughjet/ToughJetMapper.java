package com.travix.medusa.busyflights.supplier.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.dto.FlightsInput;
import com.travix.medusa.busyflights.dto.FlightsOutput;
import com.travix.medusa.busyflights.util.DateUtil;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Converts Tough Jet Objects
 *
 * @author
 */
@Component
public class ToughJetMapper extends ConfigurableMapper {

    private static final String SUPPLIER = "ToughJet";

    protected void configure(MapperFactory factory){
        factory.classMap(FlightsInput.class, ToughJetRequest.class)
                .field("origin", "from")
                .field("destination", "to")
                .field("departureDate", "outboundDate")
                .field("returnDate", "inboundDate")
                .field("numberOfPassengers", "numberOfAdults")
                .byDefault()
                .register();




        factory.classMap(ToughJetResponse.class, FlightsOutput.class)
                .field("carrier", "airline")
                .field("departureAirportName", "departureAirportCode")
                .field("arrivalAirportName", "destinationAirportCode")
                .customize(new CustomMapper<ToughJetResponse, FlightsOutput>() {
                    @Override
                    public void mapAtoB(ToughJetResponse toughJetResponse, FlightsOutput flightsOutput, MappingContext context) {
                        double discount = toughJetResponse.getBasePrice() * toughJetResponse.getDiscount() / 100;
                        double price = toughJetResponse.getBasePrice() - discount + toughJetResponse.getTax();
                        flightsOutput.setAmount(price);
                        flightsOutput.setSupplier(SUPPLIER);
                        flightsOutput.setDepartureDate(DateUtil.formatDateTime(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()), DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault()), toughJetResponse.getOutboundDateTime()));
                        flightsOutput.setArrivalDate(DateUtil.formatDateTime(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault()), DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault()), toughJetResponse.getInboundDateTime()));
                    }
                })
                .register();
    }
}
