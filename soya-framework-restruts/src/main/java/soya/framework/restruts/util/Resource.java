package soya.framework.restruts.util;

import java.io.IOException;

public interface Resource {
    String getSchema();

    Object get(String path) throws IOException;
}
