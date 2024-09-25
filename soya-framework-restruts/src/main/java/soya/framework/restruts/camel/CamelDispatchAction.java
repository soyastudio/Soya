package soya.framework.restruts.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import soya.framework.restruts.Action;
import soya.framework.restruts.ActionContext;
import soya.framework.restruts.ActionRequest;

public class CamelDispatchAction extends Action<Object> {

    public CamelDispatchAction(ActionContext context, ActionRequest request) {
        super(context, request);
    }

    @Override
    protected Object execute(ActionRequest request) throws Exception {
        CamelContext camelContext = getActionContext().getService(CamelContext.class);
        ProducerTemplate producerTemplate = getActionContext().getService(ProducerTemplate.class);

        Object o = request;
        System.out.println("================= " + request);

        Exchange exchange = ExchangeBuilder.anExchange(camelContext).withBody(request).build();
        Exchange response = producerTemplate.send("direct:restruts", exchange);


        return o;
    }
}
