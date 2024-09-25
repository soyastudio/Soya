package soya.framework.restruts;

import jakarta.servlet.http.HttpServletRequest;
import soya.framework.restruts.actions.HelloAction;
import soya.framework.restruts.camel.CamelDispatchAction;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ActionMappings {

    private final ActionMappingsLoader loader;
    private final Map<String, ActionMapping> mappings = new ConcurrentHashMap<>(new HashMap<>());

    private Set<ActionRequestMapper> getRequestMappings = new HashSet<>();
    private Set<ActionRequestMapper> postRequestMappings = new HashSet<>();
    private Set<ActionRequestMapper> putRequestMappings = new HashSet<>();
    private Set<ActionRequestMapper> deleteRequestMappings = new HashSet<>();
    private Set<ActionRequestMapper> headRequestMappings = new HashSet<>();
    private Set<ActionRequestMapper> optionsRequestMappings = new HashSet<>();

    public ActionMappings(ActionMappingsLoader loader) throws IOException {
        this.loader = loader;
        reload();

    }

    public void reload() throws IOException{
        mappings.clear();
        getRequestMappings.clear();
        postRequestMappings.clear();
        putRequestMappings.clear();
        deleteRequestMappings.clear();
        headRequestMappings.clear();
        optionsRequestMappings.clear();

        for (ActionMapping e : loader.load()) add(e);
    }

    public void add(ActionMapping mapping) {
        ActionRequestMapper mapper = mapping.getActionRequestMapper();
        String httpMethod = mapper.getHttpMethod();
        switch (httpMethod) {
            case "GET":
                getRequestMappings.add(mapper);
                break;
            case "POST":
                postRequestMappings.add(mapper);
                break;
            case "PUT":
                putRequestMappings.add(mapper);
                break;
            case "DELETE":
                deleteRequestMappings.add(mapper);
                break;
            case "HEAD":
                headRequestMappings.add(mapper);
                break;
            case "OPTIONS":
                optionsRequestMappings.add(mapper);
                break;
            default:
                throw new RuntimeException("Not supported http method: " + httpMethod);
        }

        mappings.put(mapping.getActionRequestMapper().getId(), mapping);
    }

    public ActionMapping get(HttpServletRequest request) {
        String httpMethod = request.getMethod();
        Iterator<ActionRequestMapper> iterator = switch (httpMethod) {
            case "GET" -> getRequestMappings.iterator();
            case "POST" -> postRequestMappings.iterator();
            case "PUT" -> putRequestMappings.iterator();
            case "DELETE" -> deleteRequestMappings.iterator();
            case "HEAD" -> headRequestMappings.iterator();
            case "OPTIONS" -> optionsRequestMappings.iterator();
            default -> throw new RuntimeException("Not supported http method: " + httpMethod);
        };

        while(iterator.hasNext()) {
            ActionRequestMapper key = iterator.next();
            if(key.match(request)) {
                return mappings.get(key.getId());
            }
        }

        return new ActionMapping(ActionRequestMapper.builder().create(),
                ActionDispatch.builder()
                        .actionType(CamelDispatchAction.class)
                        //.attribute("name", "Restruts")
                        .create());
    }

    public String toJson() {
        return "{}";
    }

}
