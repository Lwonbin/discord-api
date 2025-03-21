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
}
