package com.restock.app.domain.notification;

import com.restock.app.domain.product.Product;
import com.restock.app.domain.product.ProductRepository;
import com.restock.app.domain.user.ProductUserNotification;
import com.restock.app.domain.user.ProductUserNotificationHistoryRepository;
import com.restock.app.domain.user.ProductUserNotificationRepository;
import com.restock.app.interfaces.api.common.CommonRes;
import com.restock.app.interfaces.api.exception.ApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductUserNotificationRepository userNotificationRepository;

    @Mock
    private ProductNotificationHistoryRepository notificationHistoryRepository;

    @Mock
    private ProductUserNotificationHistoryRepository userNotificationHistoryRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("알림 전송 성공 케이스")
    void sendNotification() {
        // given
        long productId = 1L;
        long userId = 1L;
        boolean isActive = true;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // mock 객체로 주어질 상품 엔티티
        Product product = new Product(productId, 0L, 30L);
        ProductUserNotification productUserNotification = new ProductUserNotification(1L, productId, userId, isActive, createdAt, updatedAt);
        List<ProductUserNotification> productUserNotificationList = List.of(productUserNotification);

        // when
        when(productRepository.findById(eq(productId))).thenReturn(product);
        when(userNotificationRepository.findByProductIdAndIsActive(eq(productId), eq(true))).thenReturn(productUserNotificationList);

        // then
        CommonRes<String> result = notificationService.sendNotifications(productId);
        String resString = result.data();

        // 결과
        assertNotNull(resString);
        assertEquals(resString, "알림 처리가 완료되었습니다.");

        // 호출이 올바르게 되었는지 체크
        verify(productRepository).findById(eq(productId));
        verify(userNotificationRepository).findByProductIdAndIsActive(eq(productId), eq(true));
        verify(notificationHistoryRepository).save(any(ProductNotificationHistory.class));
        verify(notificationHistoryRepository).saveAll(any(List.class));
    }

    @Test
    @DisplayName("재고 부족시 실패")
    void sendNotificationFail() {
        // given
        long productId = 1L;

        // mock 객체로 주어질 상품 엔티티
        Product product = new Product(productId, 0L, 0L);

        // when
        when(productRepository.findById(eq(productId))).thenReturn(product);

        // then
        Exception exception = assertThrows(ApiException.class, () -> notificationService.sendNotifications(productId));

        // 결과
        assertEquals("재고 소진으로 알림 발송이 중단되었습니다.", exception.getMessage());
    }
}