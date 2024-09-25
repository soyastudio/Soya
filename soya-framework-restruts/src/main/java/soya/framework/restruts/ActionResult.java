package soya.framework.restruts;

public final class ActionResult {
    private final Object result;

    private ActionResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    static ActionResult onSuccess(Object result) {
        return new ActionResult(result);
    }

    static ActionResult onError(Throwable e) {
        return new ActionResult(e);
    }

}
