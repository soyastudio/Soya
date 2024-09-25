package soya.framework.restruts;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActionRequest implements Serializable {

    private final Map<String, Object> params;
    private final Object payload;

    private ActionRequest(Map<String, Object> params, Object payload) {
        this.params = Collections.unmodifiableMap(params);
        this.payload = payload;
    }

    public String[] getParameterNames() {
        return params.keySet().toArray(new String[params.size()]);
    }

    public Object getParameter(String name) {
        return params.get(name);
    }

    public Object getPayload() {
        return payload;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Object> params = new LinkedHashMap<>();
        private Object payload;

        private Builder() {
        }

        public Builder parameter(String name, Object value) {
            params.put(name, value);
            return this;
        }

        public Builder payload(Object payload) {
            this.payload = payload;
            return this;
        }

        public ActionRequest create() {
            return new ActionRequest(params, payload);
        }
    }

}
