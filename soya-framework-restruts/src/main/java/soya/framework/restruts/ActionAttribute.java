package soya.framework.restruts;

import java.net.URI;

public class ActionAttribute {
    private String name;
    private String value;
    private URI uri;

    public ActionAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ActionAttribute(String name, URI uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public URI getUri() {
        return uri;
    }
}
