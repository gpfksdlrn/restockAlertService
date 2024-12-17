package com.restock.app.infrastructure.repository.user;

import com.restock.app.domain.user.ProductUserNotificationHistory;
import com.restock.app.domain.user.ProductUserNotificationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {
    private final ProductUserNotificationHistoryJpaRepository jpaRepository;

    @Override
    public void saveAll(List<ProductUserNotificationHistory> userHistories) {
        jpaRepository.saveAll(userHistories);
    }

    @Override
    public List<ProductUserNotificationHistory> findByProductIdAndRestockRound(Long id, Long restockRound) {
        return jpaRepository.findByProductIdAndRestockRound(id, restockRound);
    }
}
