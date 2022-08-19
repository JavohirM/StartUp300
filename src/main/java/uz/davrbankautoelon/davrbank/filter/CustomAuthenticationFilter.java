package uz.davrbankautoelon.davrbank.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.davrbankautoelon.davrbank.dto.user.LoginResponseDto;
import uz.davrbankautoelon.davrbank.dto.user.UnAuthenticationDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String secret = "zZrq0sZK1yt9RJk51RTJ/jeU6WERbvr8nqKMWQJRX1E=";
    private final AuthenticationManager authenticationManager;
    private final JdbcTemplate jdbcTemplate;

    private String schema = "startup300";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username;
        String password;

        log.info(request.getRequestURL().toString() + "/" + request.getContextPath());
        log.info(request.getHeader("Access-Control-Allow-Origin"));
        try {
            Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            username = requestMap.get("username");
            password = requestMap.get("password");
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }

        log.info("Username is {} ", username);
        log.info("Password is {} ", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String username = user.getUsername();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 432_000_000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String query = "select sub_branch from "+schema+"._user where username = '" + username + "'";
        String getBranch = jdbcTemplate.queryForObject(query, String.class);
        LoginResponseDto dto = new LoginResponseDto();
        dto.setToken(access_token);
        dto.setBranch(getBranch);
        dto.setUsername(user.getUsername());
        dto.setRole(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        new ObjectMapper().writeValue(response.getOutputStream(), dto);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        UnAuthenticationDto dto = new UnAuthenticationDto();
        dto.setCode("401");
        dto.setMessage(failed.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), dto);
    }
}
