package com.nndmove.app.repository;

import com.nndmove.app.domain.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MovieRepositoryWithBagRelationshipsImpl implements MovieRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MOVIES_PARAMETER = "movies";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Movie> fetchBagRelationships(Optional<Movie> movie) {
        return movie.map(this::fetchGenres);
    }

    @Override
    public Page<Movie> fetchBagRelationships(Page<Movie> movies) {
        return new PageImpl<>(fetchBagRelationships(movies.getContent()), movies.getPageable(), movies.getTotalElements());
    }

    @Override
    public List<Movie> fetchBagRelationships(List<Movie> movies) {
        return Optional.of(movies).map(this::fetchGenres).orElse(Collections.emptyList());
    }

    Movie fetchGenres(Movie result) {
        return entityManager
            .createQuery("select movie from Movie movie left join fetch movie.genres where movie.id = :id", Movie.class)
            .setParameter(ID_PARAMETER, result.id)
            .getSingleResult();
    }

    List<Movie> fetchGenres(List<Movie> movies) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, movies.size()).forEach(index -> order.put(movies.get(index).id, index));
        List<Movie> result = entityManager
            .createQuery("select movie from Movie movie left join fetch movie.genres where movie in :movies", Movie.class)
            .setParameter(MOVIES_PARAMETER, movies)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.id), order.get(o2.id)));
        return result;
    }
}
