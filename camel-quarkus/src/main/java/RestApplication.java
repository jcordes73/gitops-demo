import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;

public final class RestApplication extends RouteBuilder {
    
    public void configure() {

        getContext().setManagementName("camel-quarkus");

	    restConfiguration();

        rest()
            .post("/event")
            .id("createEvent")
            .description("Creates an event")
            .consumes("application/json")
            .produces("application/json")
            .param()
                .name("body")
                .type(RestParamType.body)
                .required(true)
                .description("The event to be created")
            .endParam()
            .to("direct:createEvent");

        from("direct:createEvent")
        .to("kafka:events");
    }
}
