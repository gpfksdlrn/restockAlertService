package com.restock.app.infrastructure.repository.user;

import com.restock.app.domain.user.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUserNotificationJpaRepository extends JpaRepository<ProductUserNotification, Long> {
    List<ProductUserNotification> findByProductIdAndIsActive(Long productId, boolean isActive);
}
