package soya.framework.restruts;

import java.io.IOException;
import java.util.Set;

public interface ActionMappingsLoader {
    Set<ActionMapping> load() throws IOException;
}
