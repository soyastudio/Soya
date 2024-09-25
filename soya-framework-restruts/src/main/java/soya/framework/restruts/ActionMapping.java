package soya.framework.restruts;

public class ActionMapping {

    private final ActionRequestMapper actionRequestMapper;
    private final ActionDispatch actionDispatch;

    public ActionMapping(ActionRequestMapper actionRequestMapper, ActionDispatch actionDispatch) {
        this.actionRequestMapper = actionRequestMapper;
        this.actionDispatch = actionDispatch;
    }

    public ActionRequestMapper getActionRequestMapper() {
        return actionRequestMapper;
    }

    public ActionDispatch getActionDispatch() {
        return actionDispatch;
    }
}
