package discordBackend.discord_api.kakao;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public User oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
        String email = kakaoProfile.getKakao_account().getEmail();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(kakaoProfile));


        String token = jwtUtil.createAccessToken(user.getEmail(), user.getRole().toString());
        httpServletResponse.setHeader("Authorization", token);

        return user;

    }

    private User createNewUser(KakaoDTO.KakaoProfile kakaoProfile) {
        User newUser = AuthConverter.toUser(
                kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getNickname(),
                "ROLE_USER",  // ✅ 역할(role) 추가
                passwordEncoder
        );
        return userRepository.save(newUser);
    }

}
