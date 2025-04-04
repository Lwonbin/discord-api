package discordBackend.discord_api.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    private String password;
    private String role;

    public static User createUser(String email, String nickname, String role) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .role(role)
                .build();
    }

}
