package com.example.miniblogfrontend.security;

import com.example.miniblogfrontend.management.CurrentUserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends GenericFilterBean {

    private final CurrentUserHolder currentUserHolder;
    private List<String> ignoringPaths = List.of("/", "/login", "/logging",
            "/registration", "/register", "/latestPosts", "/index", "/styles/style.css");

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (isWhitelisted(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (currentUserHolder.getUserToken().equals("")) {
            ((HttpServletResponse) servletResponse).sendError(401);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhitelisted(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        return ignoringPaths.contains(uri);
    }
}
