package br.com.xlcode.controlli.service.impl;

import br.com.xlcode.controlli.model.entity.Usuario;
import br.com.xlcode.controlli.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiracao}")
    private String expiracao;

    @Value("${jwt.chave-assinatura}")
    private String chaveAssinatura;

    @Override
    public String gerarToken(Usuario usuario) {
        long exp = Long.parseLong(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date data = Date.from(instant);

        String horasExpiracaoToken = dataHoraExpiracao.toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        String token = Jwts
                .builder()
                .setExpiration(data)
                .setSubject(usuario.getEmail())
                .claim("nome", usuario.getNome())
                .claim("horaExpiracao", horasExpiracaoToken)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();

        return token;
    }

    @Override
    public Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJwt(token)
                .getBody();
    }

    @Override
    public boolean isTokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataEx = claims.getExpiration();
            LocalDateTime dataExpiracao = dataEx.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            boolean dataHotaAtualEDepoisDataExpiracao = LocalDateTime.now().isAfter(dataExpiracao);

            return !dataHotaAtualEDepoisDataExpiracao;
        } catch(ExpiredJwtException e) {
            return false;
        }
    }

    @Override
    public String obterLoginUsuario(String token) {
        Claims claims = obterClaims(token);

        return claims.getSubject();
    }
}
