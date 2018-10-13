package com.mytaxi.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Created by shridhar on 13/10/18.
 */

@Component
public class BasicAuthentication extends BasicAuthenticationEntryPoint
{
    @Override
    public void afterPropertiesSet() throws Exception
    {
        setRealmName("developer");
        super.afterPropertiesSet();
    }


    @Override
    public void commence(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
    {
        response.addHeader("WWW-authenticate", "Basic realm=" +getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());
    }
}
