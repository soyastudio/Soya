package soya.framework.restruts.springboot.starter;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(RestrutsProperties.class)
public class RestrutsAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    Environment environment;

    @Autowired
    private RestrutsProperties properties;

    @PostConstruct
    void init() {

    }

    @EventListener
    public void onApplicationStartedEvent(ApplicationStartedEvent event) {

    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {


    }

}
