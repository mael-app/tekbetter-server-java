package app.mael.tekbetter.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${API_KEY:default-api-key}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Authorization header is missing or invalid format");
            return false;
        }

        String token = authHeader.substring(7);

        if (!token.equals(apiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid Bearer token");
            return false;
        }

        return true;
    }
}