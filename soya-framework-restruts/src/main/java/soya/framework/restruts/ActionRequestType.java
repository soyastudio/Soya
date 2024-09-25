package soya.framework.restruts;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActionRequestType {
    private Map<String, ParamType> paramTypes = new LinkedHashMap<>();
    private Class<?> payloadType;

    public ActionRequestType() {
    }

    public void setParameter(String name, Class<?> type, boolean required, Object defaultValue) {
        ParamType paramType = new ParamType(name, type, required, defaultValue);
        paramTypes.put(name, paramType);
    }

    public void setPayloadType(Class<?> type) {
        this.payloadType = type;
    }

    public String[] parameterNames() {
        return paramTypes.keySet().toArray(new String[paramTypes.size()]);
    }

    public Class<?> parameterType(String paramName) {
        return paramTypes.get(paramName).type;
    }

    public boolean parameterRequired(String paramName) {
        return paramTypes.get(paramName).required;
    }

    public Object parameterDefaultValue(String paramName) {
        return paramTypes.get(paramName).defaultValue;
    }

    public Class<?> payloadType() {
        return payloadType;
    }


    static class ParamType {
        private final String name;
        private final Class<?> type;
        private final boolean required;
        private final Object defaultValue;

        public ParamType(String name, Class<?> type, boolean required, Object defaultValue) {
            this.name = name;
            this.type = type;
            this.required = required;
            this.defaultValue = defaultValue;
        }
    }
}
