package com.restock.app.domain.user;

import java.util.List;

public interface ProductUserNotificationHistoryRepository {
    void saveAll(List<ProductUserNotificationHistory> userHistories);
    List<ProductUserNotificationHistory> findByProductIdAndRestockRound(Long id, Long restockRound);
}
