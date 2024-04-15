package com.nndmove.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Premium.
 */
@Entity
@Table(name = "premium")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Premium implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Premium id(Long id) {
        this.setId(id);
        return this;
    }

    public Premium startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public Premium endTime(ZonedDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public Premium user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Premium)) {
            return false;
        }
        return getId() != null && getId().equals(((Premium) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Premium{" +
                "id=" + getId() +
                ", startTime='" + getStartTime() + "'" +
                ", endTime='" + getEndTime() + "'" +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public @NotNull ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public @NotNull ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(@NotNull ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(@NotNull ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
