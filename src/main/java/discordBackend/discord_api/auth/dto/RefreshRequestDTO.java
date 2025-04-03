package discordBackend.discord_api.auth.dto;

import lombok.Getter;

@Getter
public class RefreshRequestDTO {
    private String refreshToken;
}
