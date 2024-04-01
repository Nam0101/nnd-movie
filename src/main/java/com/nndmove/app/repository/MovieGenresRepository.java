package com.nndmove.app.repository;

import com.nndmove.app.domain.MovieGenres;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MovieGenres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieGenresRepository extends JpaRepository<MovieGenres, Long> {}
