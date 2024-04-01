package com.nndmove.app.repository;

import com.nndmove.app.domain.MovieResource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MovieResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieResourceRepository extends JpaRepository<MovieResource, Long> {}
