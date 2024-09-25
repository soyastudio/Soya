package soya.framework.restruts.boot.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soya.framework.restruts.*;
import soya.framework.restruts.support.DefaultActionMappingLoader;
import soya.framework.restruts.support.DefaultActionResultHandler;

import java.io.IOException;

@Configuration
public class RestrutsConfiguration {

    @Bean
    ActionContext actionContext(ApplicationContext applicationContext) {
        return new ActionContext() {
            @Override
            public String getEnvironment(String propName) {
                return applicationContext.getEnvironment().getProperty(propName);
            }

            @Override
            public <T> T getService(Class<T> type) {
                return applicationContext.getBean(type);
            }
        };
    }

    @Bean
    ActionMappings actionMappings() throws IOException {
        return new ActionMappings(new DefaultActionMappingLoader());
    }

    @Bean
    ActionDispatcher actionDispatcher(ActionContext actionContext, ActionMappings mappings) {
        return new ActionDispatcher(actionContext, mappings);
    }

    @Bean
    ServletRegistrationBean actionMappingsServlet(ActionMappings actionMappings) {
        ServletRegistrationBean bean = new ServletRegistrationBean(new ActionMappingsServlet(actionMappings),
                "/restruts/*");
        bean.setLoadOnStartup(5);
        return bean;
    }

    @Bean
    ServletRegistrationBean actionServlet(ActionDispatcher dispatcher) {
        ServletRegistrationBean bean = new ServletRegistrationBean(new ActionServlet(dispatcher, new DefaultActionResultHandler()),
                "/rest/*");
        bean.setLoadOnStartup(5);
        return bean;
    }
}
