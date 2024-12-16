package com.restock.app.domain.notification;

import com.restock.app.domain.product.Product;
import com.restock.app.domain.product.ProductRepository;
import com.restock.app.domain.user.ProductUserNotification;
import com.restock.app.domain.user.ProductUserNotificationHistory;
import com.restock.app.domain.user.ProductUserNotificationHistoryRepository;
import com.restock.app.domain.user.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository userNotificationRepository;
    private final ProductNotificationHistoryRepository notificationHistoryRepository;
    private final ProductUserNotificationHistoryRepository userNotificationHistoryRepository;

    @Transactional
    public void sendNotifications(Long productId) {
        // 상품 조회
        Product product = productRepository.findById(productId);

        // 재고가 0 이하인 경우 즉시 중단
        if (product.getStockCount() <= 0) {
            log.warn("재고가 소진되어 알림 전송이 중단되었습니다. 상품 ID = {}", productId);
            return;
        }

        // 재입고 회차 증가 - 상품의 재입고 회차를 1 증가
        product.increaseRestockRound();

        // 알림 설정된 유저 조회
        List<ProductUserNotification> users = userNotificationRepository.findByProductIdAndIsActive(productId, true);
        log.info("알림 전송 시작: 상품 ID = {}, 재입고 회차 = {}, 활성 유저 수 = {}", productId, product.getRestockRound(), users.size());

        // 알림 상태를 "IN_PROGRESS"로 저장
        notificationHistoryRepository.save(new ProductNotificationHistory(
                productId, product.getRestockRound(), NotificationStatus.IN_PROGRESS, null
        ));

        // 알림 전송 기록 저장용 리스트 생성
        List<ProductNotificationHistory> histories = new ArrayList<>();

        // 유저 알림 전송 기록 저장용 리스트 생성
        List<ProductUserNotificationHistory> userHistories = new ArrayList<>();

        // 유저별 알림 처리
        for (ProductUserNotification user : users) {
            // 재고 확인: 재고가 없으면 알림 발송 중단
            if (product.getStockCount() <= 0) {
                // "CANCELED_BY_SOLD_OUT" 상태로 기록하고 작업 중단
                histories.add(new ProductNotificationHistory(
                        productId,
                        product.getRestockRound(),
                        NotificationStatus.CANCELED_BY_SOLD_OUT,
                        user.getUserId()
                ));
                log.warn("재고 소진으로 알림 발송 중단. 상품 ID: {}, 유저 ID: {}", productId, user.getUserId());
                break;
            }

            // 알림 전송 완료 기록
            histories.add(new ProductNotificationHistory(
                    productId,
                    product.getRestockRound(),
                    NotificationStatus.COMPLETED,
                    user.getUserId()
            ));

            // 유저 알림 전송 완료 기록
            userHistories.add(new ProductUserNotificationHistory(
                    productId,
                    user.getUserId(),
                    product.getRestockRound()
            ));

            // 재고 감소
            product.decreaseStock();
        }

        // 배치 저장: 모든 알림 전송 기록을 한 번에 데이터베이스에 저장
        notificationHistoryRepository.saveAll(histories);
        userNotificationHistoryRepository.saveAll(userHistories);

        // 알림 전송 완료를 로그로 기록
        log.info("알림 진송 완료: 상품 ID = {}, 재입고 회차 = {}", productId, product.getRestockRound());
    }
}
