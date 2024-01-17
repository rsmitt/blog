package ru.blog.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, determineForwardUrl(authentication));
    }

    private String determineForwardUrl(Authentication authentication) {
        Map<String, String> successUrls = new HashMap<>();
        successUrls.put("ROLE_AUTHOR", "/dashboard/posts");
        successUrls.put("ROLE_ADMIN", "/admin/users");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority: authorities) {
            String role = grantedAuthority.getAuthority();
            if (successUrls.containsKey(role)) {
                return successUrls.get(role);
            }
        }

        throw new IllegalStateException();
    }
}
