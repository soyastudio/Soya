package soya.framework.restruts;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;

public class ActionDispatch {

    private final Class<? extends Action<?>> actionType;
    private final ActionAttribute[] actionAttributes;
    private final ActionRequestType actionRequestType;

    private ActionDispatch(Class<? extends Action<?>> actionType, ActionAttribute[] actionAttributes, ActionRequestType actionRequestType) {
        this.actionType = actionType;
        this.actionAttributes = actionAttributes;
        this.actionRequestType = actionRequestType;

        validate(actionType, actionAttributes);
    }

    private void validate(Class<? extends Action<?>> actionType, ActionAttribute[] actionAttributes) {
        System.out.println("=================== TODO: " + getClass().getName() + ".validate()");
    }

    public Class<? extends Action<?>> getActionType() {
        return actionType;
    }

    public ActionRequestType getActionRequestType() {
        return actionRequestType;
    }

    public ActionAttribute[] getActionAttributes() {
        return actionAttributes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Class<? extends Action<?>> actionType;
        private Set<ActionAttribute> actionAttributes = new LinkedHashSet<>();
        private ActionRequestType actionRequestType = new ActionRequestType();

        private Builder() {
        }

        public Builder actionType(Class<? extends Action<?>> actionType) {
            this.actionType = actionType;
            return this;
        }

        public Builder attribute(String name, String value) {
            this.actionAttributes.add(new ActionAttribute(name, value));
            return this;
        }

        public Builder attribute(String name, URI uri) {
            this.actionAttributes.add(new ActionAttribute(name, uri));
            return this;
        }

        public Builder requestParameter(String name, Class<?> type, boolean required, Object defaultValue) {
            actionRequestType.setParameter(name, type, required, defaultValue);
            return this;
        }

        public Builder requestPayloadType(Class<?> type) {
            actionRequestType.setPayloadType(type);
            return this;
        }

        public ActionDispatch create() {
            return new ActionDispatch(actionType, actionAttributes.toArray(new ActionAttribute[actionAttributes.size()]), actionRequestType);
        }
    }
}
