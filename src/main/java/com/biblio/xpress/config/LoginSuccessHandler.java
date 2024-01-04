package com.biblio.xpress.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

/**
 * This class implements the AuthenticationSuccessHandler interface to handle successful authentication events.
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * This method is called when a user has been successfully authenticated.
     *
     * @param request        the HttpServletRequest which can be used to read additional request information
     * @param response       the HttpServletResponse which can be used to influence the response sent to the user
     * @param authentication the Authentication object which was created during the authentication process
     * @throws IOException      if an input or output exception occurred
     * @throws ServletException if the request for the POST could not be handled
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Initialize the redirect URL to null
        String redirectUrl = null;

        // Get the authorities granted to the user
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Iterate over the authorities
        for (GrantedAuthority grantedAuthority : authorities) {
            // If the user has the 'ADMIN' authority, set the redirect URL to the admin dashboard
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                redirectUrl = "/admin-dashboard";
                break;
            }
            // If the user has the 'LIBRARIAN' authority, set the redirect URL to the librarian dashboard
            else if (grantedAuthority.getAuthority().equals("LIBRARIAN")) {
                redirectUrl = "/librarian-dashboard";
                break;
            }
            // If the user has the 'MEMBER' authority, set the redirect URL to the member dashboard
            else if (grantedAuthority.getAuthority().equals("MEMBER")) {
                redirectUrl = "/member-dashboard";
                break;
            }
        }

        // If no redirect URL was set (i.e., the user has none of the expected authorities), throw an exception
        if (redirectUrl == null) {
            throw new IllegalStateException();
        }

        // Redirect the user to the appropriate dashboard
        response.sendRedirect(redirectUrl);
    }
}
