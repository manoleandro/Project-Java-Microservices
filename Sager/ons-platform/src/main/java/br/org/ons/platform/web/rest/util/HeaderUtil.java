package br.org.ons.platform.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public class HeaderUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {
    	super();
    }
    
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-onsPlatformApp-alert", message);
        headers.add("X-onsPlatformApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
    	LOG.error("Entity creation failed, {}, {}", defaultMessage, errorKey);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-onsPlatformApp-error", defaultMessage);
        headers.add("X-onsPlatformApp-params", entityName);
        return headers;
    }
}
