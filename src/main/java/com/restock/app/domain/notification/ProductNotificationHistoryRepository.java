package com.restock.app.domain.notification;

import java.util.List;

public interface ProductNotificationHistoryRepository {
    void save(ProductNotificationHistory productNotificationHistory);

    void saveAll(List<ProductNotificationHistory> histories);
}
