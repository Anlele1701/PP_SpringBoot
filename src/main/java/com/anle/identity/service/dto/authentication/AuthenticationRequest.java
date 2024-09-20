package com.anle.identity.service.dto.authentication;

import com.anle.identity.service.dto.user.AbstractUserRe;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationRequest extends AbstractUserRe {
}
