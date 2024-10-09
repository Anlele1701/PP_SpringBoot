package com.anle.identity.service.dto.user.response;

import com.anle.identity.service.dto.role.RoleResponse;
import com.anle.identity.service.dto.user.AbstractUserRe;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends AbstractUserRe {
    Set<RoleResponse> roles;

}
