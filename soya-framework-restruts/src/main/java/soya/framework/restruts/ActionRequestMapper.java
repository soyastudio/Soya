package soya.framework.restruts;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

public class ActionRequestMapper {

    private final String id;
    private final String httpMethod;
    private final PathPattern pathPattern;
    private final Map<String, Class<?>> queryParams;
    private final Map<String, Class<?>> headerParams;
    private final Class<?> payloadType;

    private ActionRequestMapper(String id, String httpMethod, PathPattern pathPattern, Set<Param> qParams, Set<Param> hParams, Class<?> payloadType) {
        this.id = id;
        this.httpMethod = httpMethod;
        this.pathPattern = pathPattern;

        this.queryParams = new HashMap<>();
        qParams.forEach(e -> {
            queryParams.put(e.name, e.type);
        });

        this.headerParams = new HashMap<>();
        hParams.forEach(e -> {
            headerParams.put(e.getName(), e.getType());
        });

        this.payloadType = payloadType;
    }

    public String getId() {
        return id;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String[] getQueryParamNames() {
        return queryParams.keySet().toArray(new String[queryParams.size()]);
    }

    public Class<?> getQueryParamType(String paramName) {
        return queryParams.get(paramName);
    }

    public String[] getHeaderName() {
        return headerParams.keySet().toArray(new String[headerParams.size()]);
    }

    public Class<?> getPayloadType() {
        return payloadType;
    }

    public boolean match(HttpServletRequest request) {
        String path = request.getRequestURI();
        path = path.substring(request.getServletPath().length());
        return pathPattern.match(path);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String httpMethod;
        private PathPattern pathPattern;
        private Set<Param> queryParams = new HashSet<>();
        private Set<Param> headerParams = new HashSet<>();
        private Class<?> payloadType;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder httpMethod(String httpMethod) {
            this.httpMethod = httpMethod.toUpperCase();
            return this;
        }

        public Builder path(String pathPattern) {
            this.pathPattern = new PathPattern(pathPattern);
            return this;
        }

        public Builder addQueryParam(String queryParamName, Class<?> type) {
            this.queryParams.add(new Param(queryParamName, type));
            return this;
        }

        public Builder addHeaderParam(String headerName, Class<?> type) {
            this.headerParams.add(new Param(headerName, type));
            return this;
        }

        public Builder payloadType(Class<?> payloadType) {
            this.payloadType = payloadType;
            return this;
        }

        public ActionRequestMapper create() {
            return new ActionRequestMapper(id, httpMethod, pathPattern, queryParams, headerParams, payloadType);
        }
    }

    static class PathPattern {

        private final String path;
        private PathItem[] pathItems = {};

        PathPattern(String path) {
            this.path = path;

        }

        boolean match(String path) {
            return path.equals(this.path);
        }
    }

    static class PathItem {
        private String path;
    }

    static class Param {
        private final String name;
        private final Class<?> type;

        Param(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Class<?> getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Param param = (Param) o;
            return Objects.equals(name, param.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
    }

}
