package br.edu.ifpb.mestrado.openplanner.api.infrastructure.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.ifpb.mestrado.openplanner.api.application.configuration.properties.AccessControlHeadersProperties;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private AccessControlHeadersProperties accessControlHeadersProperties;

    public CorsFilter(AccessControlHeadersProperties accessControlHeadersProperties) {
        this.accessControlHeadersProperties = accessControlHeadersProperties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String allowOrigin = accessControlHeadersProperties.getAllowOrigin();
        Boolean allowCredentials = accessControlHeadersProperties.getAllowCredentials();

        httpServletResponse.setHeader("Access-Control-Allow-Origin", allowOrigin);
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", allowCredentials.toString());

        if ("OPTIONS".equals(httpServletRequest.getMethod())
                && (allowOrigin.equals("*") || allowOrigin.equals(httpServletRequest.getHeader("Origin")))) {
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

}
