//package discordBackend.discord_api;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//@Service
//public class JwtTokenService {
//    private final String SECRET_KEY = "your_secret_key";
//    private final InMemoryUserDetailsManager userDetailsService;
//
//    public JwtTokenService() {
//        this.userDetailsService = new InMemoryUserDetailsManager();
//    }
//
//    public String createToken(String nickname) {
//        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
//        Key key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
//
//        // 사용자 정보를 메모리에 저장 (nickname을 username으로 사용)
//        UserDetails user = User.withUsername(nickname)
//                .password("dummy")
//                .authorities("USER")
//                .build();
//        userDetailsService.createUser(user);
//
//        return Jwts.builder()
//                .setSubject(nickname)  // ⬅ 이메일 대신 닉네임 사용
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .signWith(key)
//                .compact();
//    }
//
//}