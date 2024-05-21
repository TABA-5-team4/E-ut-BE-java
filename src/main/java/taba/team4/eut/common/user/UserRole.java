package taba.team4.eut.common.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    /* binarySearch를 위해 Role 의 순서가 MemberLevel과 맞아야 한다. */

    SYS_ADMIN   ("시스템 관리자"),
    ADMIN       ("관리자"),
    USER      ("회원"),
    GUEST       ("비회원")
    ;

    private final String name;
}
