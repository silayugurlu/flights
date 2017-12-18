**Travix - Problem to be solved**

**Background:**

BusyFlights is a flights search solution which aggregates flight results initially from 2 different suppliers (CrazyAir and ToughJet). A future iteration (not part of the test) may add more suppliers.

**Solution**

REST-based API built using:

- Java 8
- Spring Boot
- Maven 

**Busy Flights API**

- Path : /busyflight/search
- Method : POST

**Request**


- Request body should be json

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| numberOfPassengers | Maximum 4 passengers |

**Response**

| Name | Description |
| ------ | ------ |
| airline | Name of Airline |
| supplier | Eg: CrazyAir or ToughJet |
| fare | Total price rounded to 2 decimals |
| departureAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| destinationAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_DATE_TIME format |
| arrivalDate | ISO_DATE_TIME format |

The service connect to the both the suppliers using HTTP.

**Trace**

-You can trace requests via path /trace

**Remaining Tasks**

- Error Handling
  Handle exceptions in detail and define error codes
- Validation suppliers response
  Validate response of suppliers, check time if it is after current time for the responses including time
- API Documentation
- Validation messages should be localized
- Logs should be written to seperate files