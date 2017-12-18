package com.travix.medusa.busyflights.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * Utility to make rest operations
 *
 * @author silay
 */
public class RestUtil {

    /**
     * Adds header and wrap in HttpEntity
     *
     * @param request
     * @param <T>
     * @return HttpEntity
     */
    public static <T> HttpEntity<T> getRequestEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(request, headers);
    }
}
