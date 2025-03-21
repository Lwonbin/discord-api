package discordBackend.discord_api.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)  // ✅ 최상위 클래스에 추가
public class KakaoDTO {

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)  // ✅ OAuthToken 클래스에도 추가
    public static class OAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private int refresh_token_expires_in;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)  // ✅ KakaoProfile 클래스에도 추가
    public static class KakaoProfile {
        private Long id;
        private String connected_at;
        private Properties properties;
        private KakaoAccount kakao_account;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)  // ✅ Properties 클래스에도 추가
        public static class Properties {
            private String nickname;
            private String profile_image;
            private String thumbnail_image;  // ✅ thumbnail_image 추가 (필요 없으면 제거 가능)
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)  // ✅ KakaoAccount 클래스에도 추가
        public static class KakaoAccount {
            private String email;
            private Boolean is_email_verified;
            private Boolean has_email;
            private Boolean profile_nickname_needs_agreement;
            private Boolean email_needs_agreement;
            private Boolean is_email_valid;
            private Profile profile;

            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)  // ✅ Profile 클래스에도 추가
            public static class Profile {
                private String nickname;
                private Boolean is_default_nickname;
            }
        }
    }
}
