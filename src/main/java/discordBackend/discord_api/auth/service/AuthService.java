package discordBackend.discord_api.auth.service;

import discordBackend.discord_api.auth.converter.AuthConverter;
import discordBackend.discord_api.auth.dto.KakaoDTO;
import discordBackend.discord_api.auth.repository.RefreshTokenRepository;
import discordBackend.discord_api.auth.util.JwtUtil;
import discordBackend.discord_api.auth.util.KakaoUtil;
import discordBackend.discord_api.user.User;
import discordBackend.discord_api.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail(), user.getRole());

        // Refresh Token을 Redis에 저장
        refreshTokenRepository.saveRefreshToken(user.getEmail(), refreshToken);

        // Access Token을 응답 헤더에 추가
        httpServletResponse.setHeader("Authorization", accessToken);
        httpServletResponse.setHeader("Refresh-Token", refreshToken);

        return user;

    }

    public String refreshAccessToken(String refreshToken) {
        String email;
        try {
            email = jwtUtil.getEmailFromToken(refreshToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT");
        }

        String storedToken = refreshTokenRepository.getRefreshToken(email);
        if (storedToken != null && storedToken.equals(refreshToken)) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
            return jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token Expired or Not Found");
    }


    private User createNewUser(KakaoDTO.KakaoProfile kakaoProfile) {
        User newUser = AuthConverter.toUser(
                kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getNickname(),
                "ROLE_USER"

        );
        return userRepository.save(newUser);
    }

}
