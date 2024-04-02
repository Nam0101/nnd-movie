package com.nndmove.app.service.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private String langKey;
    private String imageUrl;
    private String activationKey;
    private String resetKey;
    private Long resetDateId;
    private Set<Long> authoritiesIds;
}
