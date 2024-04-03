package com.nndmove.app.repository;

import com.nndmove.app.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MovieRepositoryWithBagRelationships {
    Optional<Movie> fetchBagRelationships(Optional<Movie> movie);

    List<Movie> fetchBagRelationships(List<Movie> movies);

    Page<Movie> fetchBagRelationships(Page<Movie> movies);
}
