package com.restock.app.domain.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "ProductUserNotificationHistory")
public class ProductUserNotificationHistory {
    // 유저별 재입고 알림 전송 이력 관리 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 알림 ID

    @Column(name = "product_id", nullable = false)
    private Long productId; // 상품 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 유저 ID

    @Column(name = "restock_round")
    private Long restockRound; // 재입고 회차

    @Column(name = "sent_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime sentAt; // 알림 메시지 전송 날짜
}
