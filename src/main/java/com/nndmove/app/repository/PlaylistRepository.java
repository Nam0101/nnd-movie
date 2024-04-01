package com.nndmove.app.repository;

import com.nndmove.app.domain.Playlist;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Playlist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("select playlist from Playlist playlist where playlist.user.login = ?#{authentication.name}")
    List<Playlist> findByUserIsCurrentUser();
}
