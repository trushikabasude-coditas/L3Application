package com.example.L3Application.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER="Authorization";
    private static final String PREFIX="Bearer ";
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
 @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain)
         throws ServletException, IOException {
        String token=resolveToken(request);
        if (token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            try {String username=tokenProvider.extractUsername(token);
                UserDetails user=userDetailsService.loadUserByUsername(username);
                if (tokenProvider.isTokenValid(token,user)){
                    var authentication=new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e){
                log.warn("Could not authenticate token:{}",e.getMessage());
            }
        }
        filterChain.doFilter(request,response);
    }
    private String resolveToken(HttpServletRequest request){
        String h=request.getHeader(HEADER);
        return(h !=null && h.startsWith(PREFIX)?h.substring(PREFIX.length()):null);
    }
}
