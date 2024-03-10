package com.projects.carrentalspringangular.configuration;


import com.projects.carrentalspringangular.services.jwt.UserService;
import com.projects.carrentalspringangular.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtConfigurationFilter extends OncePerRequestFilter {
    private JWTUtil jwtUtil;
    private UserService userService;
    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull  FilterChain filterChain
    ) throws ServletException, IOException {
        //first  we need to verify if there is authorisation
        final String autHeader=request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //check if autheader empty or doesn't start with bearer
        if(StringUtils.isEmpty(autHeader)||!StringUtils.startsWith(autHeader,"Bearer")){
            filterChain.doFilter(request,response);
            return;
        };
        jwt=autHeader.substring(7);
        userEmail=jwtUtil.extractUsername(jwt);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //checking if we have an userEmail(good jwt) and no authentication
        if(StringUtils.isNotEmpty(userEmail)&& authentication==null){
            UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);
            if(jwtUtil.isTokenValid(jwt,userDetails)){
                SecurityContext context=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //set our user as principal
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }
}
