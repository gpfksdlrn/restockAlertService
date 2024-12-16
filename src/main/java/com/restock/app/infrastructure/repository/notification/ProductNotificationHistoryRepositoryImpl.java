package com.restock.app.infrastructure.repository.notification;

import com.restock.app.domain.notification.ProductNotificationHistory;
import com.restock.app.domain.notification.ProductNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductNotificationHistoryRepositoryImpl implements ProductNotificationHistoryRepository {
    private final ProductNotificationHistoryJpaRepository jpaRepository;

    @Override
    public void save(ProductNotificationHistory productNotificationHistory) {
        jpaRepository.save(productNotificationHistory);
    }

    @Override
    public void saveAll(List<ProductNotificationHistory> histories) {
        jpaRepository.saveAll(histories);
    }
}
