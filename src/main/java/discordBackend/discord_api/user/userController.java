package discordBackend.discord_api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("You are logged in!");
    }
}


//TODO: role 추가,현재 로그인한 유저 받아오는 로직 추가
//TODO: DB 변경, entity 변경

