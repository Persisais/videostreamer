package persisais.videostreamer.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum{ //implements GrantedAuthority {

    USER,
    ADMIN;

/*    @Override
    public String getAuthority() {
        return String.format("ROLE_%s",name());
    }*/
}
