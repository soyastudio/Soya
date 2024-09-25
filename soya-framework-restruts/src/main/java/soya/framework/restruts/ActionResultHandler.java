package soya.framework.restruts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ActionResultHandler {
    void handle(ActionResult result, HttpServletRequest req, HttpServletResponse resp);
}
