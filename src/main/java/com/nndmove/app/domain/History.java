package com.nndmove.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A History.
 */
@Entity
@Table(name = "history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "part", nullable = false)
    private Integer part;

    @NotNull
    @Column(name = "stop_time", nullable = false)
    private Integer stopTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "genres" }, allowSetters = true)
    private Movie movie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public History id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPart() {
        return this.part;
    }

    public History part(Integer part) {
        this.setPart(part);
        return this;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public Integer getStopTime() {
        return this.stopTime;
    }

    public History stopTime(Integer stopTime) {
        this.setStopTime(stopTime);
        return this;
    }

    public void setStopTime(Integer stopTime) {
        this.stopTime = stopTime;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public History user(User user) {
        this.setUser(user);
        return this;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public History movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof History)) {
            return false;
        }
        return getId() != null && getId().equals(((History) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "History{" +
            "id=" + getId() +
            ", part=" + getPart() +
            ", stopTime=" + getStopTime() +
            "}";
    }
}
