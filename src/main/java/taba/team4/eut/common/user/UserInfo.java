package taba.team4.eut.common.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;
import java.util.Collection;

@Getter @Setter
@ToString
@Slf4j
public class UserInfo {

    private String id;
    private String userName;
    private String email;
    private String token;
    private boolean isLogin;
    private boolean isEmailVerified;
    private Collection<GrantedAuthority> roleList;

    private String pw;

    public static UserInfo getInstance(Principal principal) {
        UserInfo userInfo = new UserInfo();
        if (principal == null) return userInfo;

        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) principal;
            //log.debug("# authToken : {}", authToken);

            if (authToken != null) {
                OAuth2User oAuth2User = authToken.getPrincipal();
                //log.debug("# oAuth2User : {}", oAuth2User);

                OidcUser oidcUser = (OidcUser) authToken.getPrincipal();
                //log.debug("# oidcUser : {}", oidcUser);

                userInfo.setRoleList(authToken.getAuthorities());
                userInfo.setLogin(authToken.isAuthenticated());

                userInfo.setId(oidcUser.getSubject());
                userInfo.setToken(oidcUser.getIdToken().getTokenValue());
                userInfo.setEmail(oidcUser.getUserInfo().getEmail());
                userInfo.setEmailVerified(oidcUser.getEmailVerified());
                userInfo.setUserName(oidcUser.getUserInfo().getFullName());
            }
        }
        else if (principal instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken authToken = (JwtAuthenticationToken) principal;

            if (authToken != null) {
                Jwt jwt = authToken.getToken();

                userInfo.setRoleList(authToken.getAuthorities());
                userInfo.setLogin(authToken.isAuthenticated());

                userInfo.setId(jwt.getSubject());
                userInfo.setToken(jwt.getTokenValue());
                userInfo.setEmail(jwt.getClaim("email"));
                userInfo.setEmailVerified(jwt.getClaim("email_verified"));
                userInfo.setUserName(jwt.getClaim("name"));
            }
        }

        return userInfo;
    }

    public boolean hasRole(UserRole userRole) {
        for (GrantedAuthority authority: this.roleList) {
            if ( userRole.getName().equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}