package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("esto es el inico del filter");
        var authHeader = request.getHeader("Authorization");


        if (authHeader != null) {
            var token = authHeader.replace("Bearer", "").trim();
            var nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario != null){
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());//se fuerza a inico de secion
                SecurityContextHolder.getContext().setAuthentication(authentication);//se setea manualmente la autenticacion
            }
        }
        filterChain.doFilter(request, response);
    }
}