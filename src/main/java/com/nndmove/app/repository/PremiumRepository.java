package com.nndmove.app.repository;

import com.nndmove.app.domain.Premium;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Premium entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PremiumRepository extends JpaRepository<Premium, Long> {
    @Query("select premium from Premium premium where premium.user.login = ?#{authentication.name}")
    List<Premium> findByUserIsCurrentUser();
}
