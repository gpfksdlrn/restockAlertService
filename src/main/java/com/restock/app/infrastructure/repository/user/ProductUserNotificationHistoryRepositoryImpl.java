package com.restock.app.infrastructure.repository.user;

import com.restock.app.domain.user.ProductUserNotificationHistory;
import com.restock.app.domain.user.ProductUserNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {
    private final ProductUserNotificationHistoryJpaRepository jpaRepository;

    @Override
    public void saveAll(List<ProductUserNotificationHistory> userHistories) {
        jpaRepository.saveAll(userHistories);
    }
}
