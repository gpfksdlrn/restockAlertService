package com.restock.app.domain.user;

import java.util.List;

public interface ProductUserNotificationRepository {
    List<ProductUserNotification> findByProductIdAndIsActive(Long productId, boolean isActive);
}
