package soya.framework.restruts;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ActionDispatcher {

    private final ActionContext actionContext;
    private final ActionMappings actionMappings;
    private final ExecutorService executorService;

    public ActionDispatcher(ActionContext actionContext, ActionMappings actionMappings) {
        this.actionContext = actionContext;
        this.actionMappings = actionMappings;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public ActionDispatcher(ActionContext actionContext, ActionMappings actionMappings, ExecutorService executorService) {
        this.actionContext = actionContext;
        this.actionMappings = actionMappings;
        this.executorService = executorService;
    }

    public ActionResult dispatch(HttpServletRequest request) throws Exception {
        ActionMapping mapping = actionMappings.get(request);
        return createAction(mapping, request).call();
    }

    public Future<ActionResult> submit(HttpServletRequest request) throws Exception {
        ActionMapping mapping = actionMappings.get(request);
        return executorService.submit(createAction(mapping, request));
    }

    private Action<?> createAction(ActionMapping mapping, HttpServletRequest httpServletRequest)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ActionDispatch dispatch = mapping.getActionDispatch();
        Class<? extends Action<?>> actionType = dispatch.getActionType();
        Action<?> action = actionType
                .getConstructor(new Class<?>[]{ActionContext.class, ActionRequest.class})
                .newInstance(new Object[]{actionContext, createActionRequest(mapping, httpServletRequest)});

        Arrays.stream(dispatch.getActionAttributes()).forEach(e -> {
            Field field = getActionField(e.getName(), actionType);
            field.setAccessible(true);
            Object obj = getActionFieldValue(e.getValue(), field.getType());
            try {
                field.set(action, obj);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }

        });

        return action;
    }

    private Field getActionField(String name, Class<? extends Action<?>> actionType) {
        try {
            return actionType.getDeclaredField(name);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getActionFieldValue(String value, Class<T> type) {
        return (T) value;
    }

    private <T> T getActionFieldValue(URI uri, Class<T> type) {
        return (T) null;
    }

    private ActionRequest createActionRequest(ActionMapping mapping, HttpServletRequest httpServletRequest) {
        System.out.println("-------------------- " + mapping.getActionRequestMapper().getId());
        return ActionRequest.builder()
                .create();
    }
}
