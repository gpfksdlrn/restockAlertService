package com.restock.app.domain.notification;

import com.restock.app.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ProductNotificationHistory")
public class ProductNotificationHistory {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "restock_round")
    private Long restockRound; // 재입고 회차

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private NotificationStatus notificationStatus;

    @Column(name = "last_sent_user_id")
    private Long lastSentUserId;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    // Enum 정의
    public enum NotificationStatus {
        IN_PROGRESS,
        CANCELED_BY_SOLD_OUT,
        CANCELED_BY_ERROR,
        COMPLETED
    }
}
