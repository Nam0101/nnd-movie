package com.nndmove.app.service.dto;

import com.nndmove.app.domain.User;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;
    private boolean activated;
    private String activationKey;
    private String createdBy;
    private Instant createdDate;
    private String email;
    private String firstName;
    private String imageUrl;
    private String langKey;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private String lastName;
    private String passwordHash;
    private Instant resetDate;
    private String resetKey;

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        // Add the new fields here
        this.activated = user.isActivated();
        this.activationKey = user.getActivationKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.lastName = user.getLastName();
        this.passwordHash = user.getPassword();
        this.resetDate = user.getResetDate();
        this.resetKey = user.getResetKey();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", activated=" + activated +
            ", activationKey='" + activationKey + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", langKey='" + langKey + '\'' +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastName='" + lastName + '\'' +
            ", passwordHash='" + passwordHash + '\'' +
            ", resetDate=" + resetDate +
            ", resetKey='" + resetKey + '\'' +
            "}";
    }
}
