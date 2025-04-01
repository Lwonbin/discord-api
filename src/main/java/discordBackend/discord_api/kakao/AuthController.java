package discordBackend.discord_api.kakao;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    public ResponseEntity<User> kakaoLogin(@RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        User user = authService.oAuthLogin(accessCode, httpServletResponse);
        return ResponseEntity.ok(user);
    }

    // Refresh Token을 이용한 Access Token 재발급
    @GetMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
        String newAccessToken = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }

    // 로그아웃 API (Refresh Token 삭제)
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("email") String email) {
        authService.logout(email);
        return ResponseEntity.ok("Logged out successfully.");
    }
}
