package soya.framework.restruts.util;

import java.io.IOException;

public class EnvironmentResource implements Resource {
    private static final String SCHEMA = "env";
    private static final EnvironmentResource INSTANCE = new EnvironmentResource();

    private EnvironmentResource() {
    }

    static EnvironmentResource instance() {
        return INSTANCE;
    }

    @Override
    public String getSchema() {
        return SCHEMA;
    }

    @Override
    public Object get(String path) throws IOException {
        return null;
    }
}
