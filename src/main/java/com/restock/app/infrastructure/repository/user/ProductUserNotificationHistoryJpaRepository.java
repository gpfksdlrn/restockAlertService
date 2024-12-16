package com.restock.app.infrastructure.repository.user;

import com.restock.app.domain.user.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationHistoryJpaRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
}
