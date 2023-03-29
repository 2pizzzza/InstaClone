package com.example.InstaClone.security;

import com.example.InstaClone.entity.User;
import com.example.InstaClone.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    public static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(httpServletRequest);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromToke(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception ex) {
            LOG.error("Could not set user athentication");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConstants.TOKENP_PREFIX)) {
            return bearToken.split(" ")[1];
        }
        return null;
    }
}
