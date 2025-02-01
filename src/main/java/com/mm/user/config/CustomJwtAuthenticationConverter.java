package com.mm.user.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.type.TypeFactory;
// import com.fasterxml.jackson.databind.util.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    // @Override
    // public JwtAuthenticationToken convert(Jwt jwt) {
    // Extract roles or authorities from the JWT
    // Collection<GrantedAuthority> authorities =
    // jwt.getClaimAsStringList("roles").stream()
    // .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
    // .collect(Collectors.toList());

    // // Create and return JwtAuthenticationToken
    // return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    // }

    private JwtAuthenticationConverter jwtAuthenticationConverter;
    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    public CustomJwtAuthenticationConverter() {
        this.jwtAuthenticationConverter = new JwtAuthenticationConverter();
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);

        return new JwtAuthenticationToken(jwt, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // if(jwt.getClaim("resource_access") != null) {
        // Map<String, Object> realmAccess = jwt.getClaim("resource_access");
        // Map<String, Object> staticApp = (Map<String, Object>)
        // realmAccess.get("static-app");
        // ObjectMapper mapper = new ObjectMapper();
        // //List<String> roles = mapper.convertValue(realmAccess.get("roles"), new
        // TypeReference<List<String>>(){});
        // List<String> roles = mapper.convertValue(staticApp.get("roles"), new
        // TypeReference<List<String>>(){});
        // List<GrantedAuthority> authorities = new ArrayList<>();

        // for (String role : roles) {
        // authorities.add(new SimpleGrantedAuthority(role));
        // }

        // return authorities;
        // }
        // return new ArrayList<>();

        // Initialize an empty list of authorities
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Get the "resource_access" claim
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return authorities; // Return empty authorities if claim is missing
        }

        // Get "static-app" access information
        Map<String, Object> staticApp = (Map<String, Object>) resourceAccess.get("static-app");
        if (staticApp == null) {
            return authorities; // Return empty authorities if "static-app" is missing
        }

        // Extract roles from "static-app"
        Object rolesObj = staticApp.get("roles");
        if (rolesObj == null) {
            return authorities; // Return empty authorities if "roles" is missing
        }

        // Safely map roles to a list
        List<String> roles;
        try {
            roles = new ObjectMapper().convertValue(rolesObj, new TypeReference<List<String>>() {
            });
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing roles: " + e.getMessage());
            return authorities; // Return empty authorities if parsing fails
        }

        // Convert roles to GrantedAuthority and add to the list
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return authorities;
    }

    // @Override
    // public JavaType getInputType(TypeFactory typeFactory) {
    // return typeFactory.constructType(Jwt.class);
    // }

    // @Override
    // public JavaType getOutputType(TypeFactory typeFactory) {
    // return typeFactory.constructType(JwtAuthenticationToken.class);
    // }
}
