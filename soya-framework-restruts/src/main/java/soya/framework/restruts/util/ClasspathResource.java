package soya.framework.restruts.util;

import java.io.IOException;

public final class ClasspathResource implements Resource {
    private static final String SCHEMA = "classpath";
    private static final ClasspathResource INSTANCE = new ClasspathResource();

    private ClasspathResource() {
    }

    static ClasspathResource instance() {
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
