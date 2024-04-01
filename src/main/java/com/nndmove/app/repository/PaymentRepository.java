package com.nndmove.app.repository;

import com.nndmove.app.domain.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Payment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select payment from Payment payment where payment.user.login = ?#{authentication.name}")
    List<Payment> findByUserIsCurrentUser();
}
