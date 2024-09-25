package soya.framework.restruts.util;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public final class Resources {

    private static final Map<String, Resource> resourceMap;

    static {
        resourceMap = new HashMap<>();
        resourceMap.put(ClasspathResource.instance().getSchema(), ClasspathResource.instance());
    }

    public static Object fromURI(URI uri) {
        String schema = uri.getScheme();
        String path = uri.getScheme();
        try {
            return resourceMap.get(schema).get(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
