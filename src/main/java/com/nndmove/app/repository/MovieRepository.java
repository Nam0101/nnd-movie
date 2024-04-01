package com.nndmove.app.repository;

import com.nndmove.app.domain.Movie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Movie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {}
