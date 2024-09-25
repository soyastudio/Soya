package soya.framework.restruts;

import java.util.concurrent.Callable;

public interface ActionCallable extends Callable<ActionResult> {
    ActionContext getActionContext();
}
