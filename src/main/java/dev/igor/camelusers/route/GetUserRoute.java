package dev.igor.camelusers.route;

import dev.igor.camelusers.dto.UserResponse;
import dev.igor.camelusers.error.ApplicationError;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class GetUserRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:get-users")
            .doTry()
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/users/${header.document}"))
                .to("{{users.url}}")
                .unmarshal().json(JsonLibrary.Jackson, UserResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
