package discordBackend.discord_api.auth.converter;

import discordBackend.discord_api.user.User;

public class AuthConverter {
    public static User toUser(String email, String nickname) {
        return User.createUser(email, nickname);
    }
}

