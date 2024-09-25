package soya.framework.restruts.support;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import soya.framework.restruts.ActionDispatch;
import soya.framework.restruts.ActionMapping;
import soya.framework.restruts.ActionMappingsLoader;
import soya.framework.restruts.ActionRequestMapper;
import soya.framework.restruts.actions.HelloAction;
import soya.framework.restruts.camel.CamelDispatchAction;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultActionMappingLoader implements ActionMappingsLoader {
    private OpenAPI openAPI;

    @Override
    public Set<ActionMapping> load() throws IOException {

        Set<ActionMapping> mappings = new LinkedHashSet<>();

        SwaggerParseResult result = new OpenAPIV3Parser().readLocation("https://petstore3.swagger.io/api/v3/openapi.json", null, null);
        this.openAPI = result.getOpenAPI();

        openAPI.getPaths().forEach((path, pathItem) -> {
            if (pathItem.getGet() != null) {
                mappings.add(createActionMapping(path, "GET", pathItem.getGet()));

            } else if (pathItem.getPost() != null) {
                mappings.add(createActionMapping(path, "POST", pathItem.getPost()));

            } else if (pathItem.getPut() != null) {
                mappings.add(createActionMapping(path, "PUT", pathItem.getPut()));

            } else if (pathItem.getDelete() != null) {
                mappings.add(createActionMapping(path, "DELETE", pathItem.getDelete()));

            } else if (pathItem.getHead() != null) {
                mappings.add(createActionMapping(path, "HEAD", pathItem.getHead()));

            } else if (pathItem.getOptions() != null) {
                mappings.add(createActionMapping(path, "OPTIONS", pathItem.getOptions()));

            } else if (pathItem.getHead() != null) {
                mappings.add(createActionMapping(path, "HEAD", pathItem.getHead()));

            } else if (pathItem.getPatch() != null) {
                mappings.add(createActionMapping(path, "PATCH", pathItem.getPatch()));

            } else if (pathItem.getTrace() != null) {
                mappings.add(createActionMapping(path, "TRACE", pathItem.getTrace()));

            }
        });

        return mappings;
    }

    private ActionMapping createActionMapping(String path, String method, Operation operation) {

        ActionRequestMapper.Builder requestMapperBuilder = ActionRequestMapper.builder();
        ActionDispatch.Builder dispatchBuilder = ActionDispatch.builder();

        requestMapperBuilder
                .id(operation.getOperationId())
                .path(path)
                .httpMethod(method);

        dispatchBuilder
                .actionType(CamelDispatchAction.class);

        operation.getOperationId();

        return new ActionMapping(requestMapperBuilder.create(), dispatchBuilder.create());
    }


}
