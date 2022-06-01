package com.trainingapps.userms.util;


import com.trainingapps.userms.dto.UserDetails;
import com.trainingapps.userms.exceptions.InvalidTokenException;
import com.trainingapps.userms.exceptions.UserNotFoundException;
import com.trainingapps.userms.service.IUserService;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFiler implements Filter {

    @Autowired
    private IUserService service;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader("token");
        System.out.println("***inside filter request method=" + httpRequest.getMethod() + "url=" + httpRequest.getRequestURL() + " token=" + token);
        try {
            UserDetails user = service.authenticateByToken(token);
            httpRequest.setAttribute("username", user.getUsername());
            chain.doFilter(httpRequest, httpResponse);
            return;
        } catch (InvalidTokenException | UserNotFoundException | MalformedJwtException e) {
            PrintWriter writer = httpResponse.getWriter();
            writer.write("invalid token, You are unauthorized !");
            int statusCode = HttpStatus.UNAUTHORIZED.value();
            httpResponse.setStatus(statusCode);
        }

    }
}
