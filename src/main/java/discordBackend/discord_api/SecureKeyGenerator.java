package discordBackend.discord_api;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;

public class SecureKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);  // ✅ 안전한 키 생성
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Secure Key: " + encodedKey);
    }
}