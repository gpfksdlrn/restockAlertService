package com.restock.app.infrastructure.repository.user;

import com.restock.app.domain.user.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUserNotificationHistoryJpaRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
    List<ProductUserNotificationHistory> findByProductIdAndRestockRound(Long id, Long restockRound);
}
