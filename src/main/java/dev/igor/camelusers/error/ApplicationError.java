package dev.igor.camelusers.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.igor.camelusers.error.response.ResponseError;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.http.MediaType;

public class ApplicationError implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        HttpOperationFailedException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);

        switch (exception.getStatusCode()) {
            case 404 -> {
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, exception.getStatusCode());
                exchange.getMessage().setBody(null);
            }
            case 400 -> {
                ObjectMapper mapper = new ObjectMapper();
                ResponseError responseError = mapper.readValue(exception.getResponseBody(), ResponseError.class);
                exchange.getMessage().setBody(responseError);
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, exception.getStatusCode());
                exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            }
            default -> exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
        }
    }
}
