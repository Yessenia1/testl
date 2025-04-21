package pe.edu.upeu.sysalmacen.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORS implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // El método init está vacío porque no es necesario inicializar ningún recurso antes de que se ejecute el filtro.
        // Si fuera necesario realizar alguna acción de inicialización, se implementaría aquí.
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
        // Configuración de los encabezados de CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
        
        // Si la solicitud es OPTIONS (pre-flight), respondemos con un estado 200 OK
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // De lo contrario, continuamos con la cadena de filtros
            chain.doFilter(req, res);
        }
    }
    
    @Override
    public void destroy() {
        // Este método está vacío porque no se necesita liberar recursos en este filtro.
        // Si se necesitara liberar algún recurso, como conexiones o hilos, se implementaría aquí.
    }
}
