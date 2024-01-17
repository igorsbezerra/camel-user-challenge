package dev.igor.camelusers.route;


import dev.igor.camelusers.dto.UserRequest;
import dev.igor.camelusers.dto.UserResponse;
import dev.igor.camelusers.error.ApplicationError;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class PostUserRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:post-users")
            .doTry()
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/users"))
                .marshal().json(JsonLibrary.Jackson, UserRequest.class)
                .to("{{users.url}}")
                .unmarshal().json(JsonLibrary.Jackson, UserResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
