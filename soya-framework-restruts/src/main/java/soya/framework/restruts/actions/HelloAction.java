package soya.framework.restruts.actions;

import soya.framework.restruts.Action;
import soya.framework.restruts.ActionContext;
import soya.framework.restruts.ActionRequest;

import java.net.URI;

public class HelloAction extends Action<String> {

    private String name;

    public HelloAction(ActionContext context, ActionRequest request) {
        super(context, request);
    }

    @Override
    protected String execute(ActionRequest request) throws Exception {
        URI uri = new URI("context:rest-dispatch.json");
        System.out.println("----------------- " + uri.getScheme());
        System.out.println("----------------- " + uri.getSchemeSpecificPart());


        System.out.println("==================== Hello: " + name);
        return "Hello";
    }
}
