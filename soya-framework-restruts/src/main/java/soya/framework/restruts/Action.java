package soya.framework.restruts;

public abstract class Action<T> implements ActionCallable {

    private final ActionContext context;
    private final ActionRequest request;

    public Action(ActionContext context, ActionRequest request) {
        this.context = context;
        this.request = request;
    }

    public ActionContext getActionContext() {
        return context;
    }

    @Override
    public ActionResult call() {
        try {
            return ActionResult.onSuccess(execute(request));

        } catch (Exception e) {
            return ActionResult.onError(e);

        }
    }

    protected abstract T execute(ActionRequest request) throws Exception;
}
