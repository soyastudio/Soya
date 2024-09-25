package soya.framework.restruts;

public interface ActionContext {
    String getEnvironment(String propName);

    <T> T getService(Class<T> type);
}
