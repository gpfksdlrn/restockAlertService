package com.restock.app.infrastructure.repository.notification;

import com.restock.app.domain.notification.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryJpaRepository extends JpaRepository<ProductNotificationHistory, Long> {
}
