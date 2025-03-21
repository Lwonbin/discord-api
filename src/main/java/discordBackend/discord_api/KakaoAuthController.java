//package discordBackend.discord_api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth/kakao")
//public class KakaoAuthController {
//
//    private final String clientId = "YOUR_KAKAO_CLIENT_ID";
//    private final String clientSecret = "YOUR_KAKAO_CLIENT_SECRET";  // 🔹 필요 시 포함
//    private final String redirectUri = "http://localhost:8080/auth/kakao/callback";
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @GetMapping("/callback")
//    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
//        System.out.println("✅ Received Authorization Code: " + code);
//
//        String tokenUrl = "https://kauth.kakao.com/oauth/token";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//
//        // 🔹 client_secret은 활성화된 경우 포함해야 함
//        String body = "grant_type=authorization_code"
//                + "&client_id=" + clientId
////                + "&client_secret=" + clientSecret  // 🔹 필요 없으면 제거
//                + "&redirect_uri=" + redirectUri
//                + "&code=" + code;
//
//        System.out.println("🔹 Sending Access Token Request: " + body);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
//        ResponseEntity<String> response;
//
//        try {
//            response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);
//            System.out.println("🔹 Raw Access Token Response: " + response.getBody()); // ✅ 응답 확인
//        } catch (Exception e) {
//            System.err.println("❌ Access Token Request Failed: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to get access token: " + e.getMessage());
//        }
//
//        if (response.getStatusCode() != HttpStatus.OK) {
//            System.err.println("❌ Failed to get access token: " + response.getBody());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to get access token: " + response.getBody());
//        }
//
//        // JSON 파싱
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> responseBody;
//        try {
//            responseBody = objectMapper.readValue(response.getBody(), Map.class);
//        } catch (Exception e) {
//            System.err.println("❌ Failed to parse Access Token response: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to parse access token response");
//        }
//
//        String accessToken = (String) responseBody.get("access_token");
//        System.out.println("✅ Received Access Token: " + accessToken);
//
//        return ResponseEntity.ok(Map.of("token", accessToken));
//    }
//}
