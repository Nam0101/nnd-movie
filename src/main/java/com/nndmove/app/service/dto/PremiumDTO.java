package com.nndmove.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.nndmove.app.domain.Premium} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PremiumDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private ZonedDateTime endTime;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PremiumDTO)) {
            return false;
        }

        PremiumDTO premiumDTO = (PremiumDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, premiumDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PremiumDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
