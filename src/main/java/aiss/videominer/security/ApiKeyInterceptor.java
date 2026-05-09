package aiss.videominer.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
    // Sacamos la clave desde el archivo application.properties
    @Value("${videominer.api.key}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // Buscamos en las cabeceras de la petición el campo "X-API-KEY"
        String requestApiKey = request.getHeader("X-API-KEY");
        // Si la clave existe y coincide con la nuestra, le dejamos pasar (true)
        if ( requestApiKey !=null && requestApiKey.equals(apiKey)){
            return true;
        } else {
            // Si no, paramos la petición y devolvemos error 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Error 401:The user is not logged in (API Key no valida o ausente)");
            return false;
        }
    }

}
