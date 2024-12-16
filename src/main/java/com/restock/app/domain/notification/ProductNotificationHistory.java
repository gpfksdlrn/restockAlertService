package com.restock.app.domain.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "PRODUCT_NOTIFICATION_HISTORY")
public class ProductNotificationHistory {
    // 상품별 재입고 알림 전송 이력을 관리하는 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId; // 상품 참조

    @Column(name = "restock_round", nullable = false)
    private Long restockRound; // 재입고 회차

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private NotificationStatus notificationStatus;

    @Column(name = "last_sent_user_id")
    private Long lastSentUserId;

    public ProductNotificationHistory(Long productId, Long restockRound, NotificationStatus notificationStatus, Long lastSentUserId) {
        this.productId = productId;
        this.restockRound = restockRound;
        this.notificationStatus = notificationStatus;
        this.lastSentUserId = lastSentUserId;
    }
}
