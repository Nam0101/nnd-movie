package com.nndmove.app.repository;

import com.nndmove.app.domain.History;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the History entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select history from History history where history.user.login = ?#{authentication.name}")
    List<History> findByUserIsCurrentUser();
}
