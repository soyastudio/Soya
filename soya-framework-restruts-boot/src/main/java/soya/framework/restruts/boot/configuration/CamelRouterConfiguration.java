package soya.framework.restruts.boot.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelRouterConfiguration extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:restruts").id("first").log("RESTRUTS!");

    }
}
