package com.nndmove.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.nndmove.app.domain.Premium} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PremiumDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
