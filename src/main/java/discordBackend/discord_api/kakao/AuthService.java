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
    private final RefreshTokenRepository refreshTokenRepository;

    public User oAuthLogin(String accessCode, HttpServletResponse httpServletResponse) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(accessCode);
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
        String email = kakaoProfile.getKakao_account().getEmail();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(kakaoProfile));


        // Access Token & Refresh Token 생성
        String accessToken = jwtUtil.createAccessToken(user.getEmail(), user.getRole().toString());
        String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

        // Refresh Token을 Redis에 저장
        refreshTokenRepository.saveRefreshToken(user.getEmail(), refreshToken);

        // Access Token을 응답 헤더에 추가
        httpServletResponse.setHeader("Authorization", accessToken);
        httpServletResponse.setHeader("Refresh-Token", refreshToken);

        return user;

    }

    public String refreshAccessToken(String refreshToken) {
        String email = jwtUtil.getEmailFromToken(refreshToken);
        String storedToken = refreshTokenRepository.getRefreshToken(email);

        if (storedToken != null && storedToken.equals(refreshToken)) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
            return jwtUtil.createAccessToken(user.getEmail(), user.getRole().toString());
        }

        throw new RuntimeException("Invalid Refresh Token");
    }

    public void logout(String email) {
        refreshTokenRepository.deleteRefreshToken(email);
    }

    private User createNewUser(KakaoDTO.KakaoProfile kakaoProfile) {
        User newUser = AuthConverter.toUser(
                kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getNickname(),
                "ROLE_USER",
                passwordEncoder
        );
        return userRepository.save(newUser);
    }

}
