package discordBackend.discord_api.auth.converter;

import discordBackend.discord_api.user.User;

public class AuthConverter {
    public static User toUser(String email, String nickname, String role) {
        return User.createUser(email, nickname, role);
    }
}

