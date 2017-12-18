package com.travix.medusa.busyflights.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ErrorResponse {

    private String message;
}
