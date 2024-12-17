package com.restock.app.infrastructure.repository.user;

import com.restock.app.domain.user.ProductUserNotification;
import com.restock.app.domain.user.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository {
    private final ProductUserNotificationJpaRepository jpaRepository;
    
    @Override
    public List<ProductUserNotification> findByProductIdAndIsActive(Long productId, boolean isActive) {
        return jpaRepository.findByProductIdAndIsActive(productId, isActive);
    }
}
