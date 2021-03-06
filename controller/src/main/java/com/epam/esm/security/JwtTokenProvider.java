package com.epam.esm.security;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.userdetails_service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * JwtTokenProvider class helps provide methods for obtaining and setting up the JWT token
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secretWord}")
    private String secretWord;
    @Value("${jwt.token.expired}")
    private Long expireTokenTime;

    private final CustomUserDetailsService customUserDetailsService;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer_";

    @Autowired
    public JwtTokenProvider(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    /**
     * Method createToken helps to create the token
     * @param user User user
     * @return Token entity
     */
    public String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getLogin());
        claims.put("roles",getRoleNames(user.getRoles()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTokenTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    /**
     * Method getAuthentication assists in obtaining authentication for the transferred token
     * @param token String token
     * @return UsernamePasswordAuthenticationToken entity
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * The getLogin method helps obtain the tokens login
     * @param token String token
     * @return String login
     */
    public String getLogin(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * The resolveToken method helps get the token body from the header "Authorization"
     * @param req HttpServletRequest request
     * @return String login
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * The validateToken method helps validate the token
     * @param token String token
     * @return boolean isValid
     */
    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch(JwtException | IllegalArgumentException exception){
            return false;
        }
    }

    private List<String> getRoleNames(List<Role> roleList){
        List<String> roleNames = new ArrayList<>();
        roleList.forEach(role -> roleNames.add(role.getName()));
        return roleNames;
    }

}
