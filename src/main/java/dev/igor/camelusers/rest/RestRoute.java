package dev.igor.camelusers.rest;

import dev.igor.camelusers.dto.UserRequest;
import dev.igor.camelusers.dto.UserResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .apiContextRouteId("swagger")
                .component("servlet")
                .contextPath("")
                .apiContextPath("/swagger")
                .apiProperty("api.title", "Swagger Service User")
                .apiProperty("api.description", "Swagger Service User")
                .apiProperty("api.version", "Swagger Service User")
                .apiProperty("host", "localhost")
                .apiProperty("port", "8080")
                .apiProperty("schemes", "http")
                .bindingMode(RestBindingMode.auto);

        rest("/users")
                .get("/{document}")
                    .id("rest-users-find-by-documents")
                    .description("Method responsible for find by account by document")
                    .bindingMode(RestBindingMode.auto)

                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .outType(UserResponse.class)

                    .responseMessage()
                        .code(HttpStatus.OK.value())
                        .message("OK")
                    .endResponseMessage()
                .to("direct:get-users")
                .post()
                    .id("rest-users-create")
                    .description("Method responsible to create user")
                    .bindingMode(RestBindingMode.auto)

                    .type(UserRequest.class)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .outType(UserResponse.class)

                    .responseMessage()
                        .code(HttpStatus.CREATED.value())
                        .message("CREATED")
                    .endResponseMessage()
                .to("direct:post-users");
    }
}
