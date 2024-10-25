package LuQ.Music_hall.security;


import LuQ.Music_hall.entities.Utente;
import LuQ.Music_hall.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new BadRequestException("Inserisci correttamente il token nell'autorizzazione");
        String Token = authHeader.substring(7);
        jwtTools.verifyToken(Token);
        String id = jwtTools.takeIdFromToken(Token);
        Utente utenteCorrente = this.utenteService.findById(id);
        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteCorrente, null, utenteCorrente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // return new AntPathMatcher().match("/auth/**", request.getServletPath());
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/auth/**", request.getServletPath()) ||
                pathMatcher.match("/sale/auth/**", request.getServletPath()) ||
                pathMatcher.match("/sale", request.getServletPath());


    }
}
