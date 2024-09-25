package soya.framework.restruts;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class ActionMappingsServlet extends HttpServlet {
    private final ActionMappings actionMappings;
    private Map<String, Method> methodMap = new HashMap<>();

    public ActionMappingsServlet(ActionMappings actionMappings) {
        this.actionMappings = actionMappings;
        Method[] methods = getClass().getDeclaredMethods();
        Arrays.stream(methods).forEach(m -> {
            String methodName = m.getName();
            Class<?>[] paramTypes = m.getParameterTypes();

            if (Modifier.isPublic(m.getModifiers())
                    && paramTypes.length == 2
                    && HttpServletRequest.class.isAssignableFrom(paramTypes[0])
                    && HttpServletResponse.class.isAssignableFrom(paramTypes[1])
            ) {
                methodMap.put(m.getName().toUpperCase(), m);

            }
        });

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Method method = getMethod(req);
        if (method == null) {
            help(req, resp);
        } else {
            dispatch(method, req, resp);
        }
    }

    public void help(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("----------- help...");
    }

    public void api(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OutputStream out = resp.getOutputStream();
        String json = actionMappings.toJson();
        out.write(json.getBytes());
        out.close();
    }

    public void reload(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        try {
            actionMappings.reload();

        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    private void dispatch(Method method, HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            method.invoke(this, new Object[]{req, resp});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ServletException(e);
        }
    }

    private Method getMethod(HttpServletRequest req) {
        String uri = req.getRequestURI();
        String path = req.getContextPath() + req.getServletPath();
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        String methodName = uri.substring(path.length());
        return methodMap.get(methodName.toUpperCase());
    }
}
