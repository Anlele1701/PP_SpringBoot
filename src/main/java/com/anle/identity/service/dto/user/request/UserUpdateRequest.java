package com.anle.identity.service.dto.user.request;

import com.anle.identity.service.dto.user.AbstractUserRe;
import lombok.*;

import java.util.List;
import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateRequest extends AbstractUserRe {
    Set<String> roles;

}
