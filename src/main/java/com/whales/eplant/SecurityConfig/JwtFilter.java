package com.whales.eplant.SecurityConfig;//package com.oracleous.extention_manager.securityConfiguration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtService ;

    @Autowired
    private ApplicationContext context;

    protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response , FilterChain filterChain) throws ServletException , IOException{
        String authHeader = request.getHeader("Authorization");
        String token = null ;
        String userName = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            System.out.println("2   token:: " + token);
            userName = jwtService.extractUsername(token);
            System.out.println("3   userName:: " + userName);
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            MyUserDetailsService userDetailsService = context.getBean(MyUserDetailsService.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            System.out.println("4   This is user details " + userDetails.getUsername());
//            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loyadUserByUsername(userName);
            if(jwtService.validateToken(token, String.valueOf(userDetails))) { // we create  Method validate inside jwtService class
                System.out.println("5   Token validated");

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// this request here is the httpServet above
                System.out.println("6   auth token" + authToken);
                System.out.println("7   userDetails {}" + userDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                System.out.println("8.  Token not validated");
            }
        }
        filterChain.doFilter(request , response);
    }
}