package com.fms.app.web;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * @author Omid Pourhadi
 *
 */
public class AuthLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler
{


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
//            String userName = (String) session.getAttribute(AuthSuccessHandler.CURRENT_USERNAME);
            session.removeAttribute(AuthSuccessHandler.CURRENT_USERNAME);
            session.removeAttribute(AuthSuccessHandler.CURRENT_USERID);
//            cacheService.clearEntityById();
//            cacheService.clearPrivateCache();
        }
        super.handle(request, response, authentication);
    }

}