package com.anle.identity.service.dto.authentication;

import com.anle.identity.service.dto.user.AbstractUserRe;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class AuthenticationResponse{
    boolean authenticated;
}
