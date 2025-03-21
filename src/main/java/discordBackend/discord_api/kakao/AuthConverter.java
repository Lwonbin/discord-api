package discordBackend.discord_api.kakao;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConverter {
    public static User toUser(String email, String nickname, String role, PasswordEncoder passwordEncoder) {
        return User.createUser(email, nickname, role);
    }
}

