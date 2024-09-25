package soya.framework.restruts.boot;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

@SpringBootApplication
@EnableCaching
public class RestrutsApplication {
    public static final String WORKSPACE_HOME = "workspace.home";

    public static void main(String[] args) {
        init();
        SpringApplication.run(RestrutsApplication.class, args);
    }

    private static void init() {
        String url = RestrutsApplication.class.getProtectionDomain().getCodeSource().getLocation().toString();
        if(url.indexOf("!") > 0) {
            url = url.substring(0, url.indexOf("!"));
        }

        if(url.startsWith("jar:")) {
            url = url.substring("jar:".length());
        }

        File file = Paths.get(URI.create(url)).toFile();
        File home = file.getParentFile().getParentFile();

        System.setProperty(WORKSPACE_HOME, home.getAbsolutePath());

        System.out.println("-------------------- init(): " + System.getProperty(WORKSPACE_HOME));

    }

    @EventListener(classes = {ApplicationReadyEvent.class})
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        // System.out.println("================== " + applicationContext.getBean(ApiManager.class));

        /*ActionProxyFactory factory = applicationContext.getBean(ActionProxyFactory.class);
        Workshop workshop = factory.create(Workshop.class);

        String encoded = workshop.base64Encode("SUCCESS!");
        String decoded = workshop.base64Decode(encoded);

        System.out.println("-------------------- workspace.home = " + ActionContext.getInstance().getProperty("workspace.home"));
        System.out.println("---------------------- encoded: " + encoded);
        System.out.println("---------------------- decoded: " + decoded);*/

        CamelContext camel = applicationContext.getBean(CamelContext.class);
        camel.getRoutes().forEach(e -> {
            System.out.println("---------------- " + e.getId());
        });

        ProducerTemplate producerTemplate = applicationContext.getBean(ProducerTemplate.class);
        System.out.println("---------------- " + producerTemplate);
    }
}
