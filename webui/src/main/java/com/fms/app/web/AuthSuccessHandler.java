package com.fms.app.web;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fms.app.domain.UserEntity;;

/**
 * @author Omid Pourhadi
 *
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{

    public static final String CURRENT_USERNAME = "currentUsername";
    public static final String CURRENT_USERID = "currentUserId";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        if (authentication != null && authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();
            boolean anonymous = principal instanceof String || principal.equals("anonymousUser");
            if (!anonymous)
            {
                HttpSession session = request.getSession(false);
                if (session != null)
                {
                    if (principal instanceof UserEntity)
                    {
                    	UserEntity user = ((UserEntity) principal);
                        setValues(request, principal, session, user.getId(), user.getUsername());
                    }
                }
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void setValues(HttpServletRequest request, Object principal, HttpSession session, Long userId, String username)
    {
        session.setAttribute(CURRENT_USERNAME, username);
        session.setAttribute(CURRENT_USERID, userId);
        

        setDefaultTargetUrl("/secure/dashboard");
    }

}
