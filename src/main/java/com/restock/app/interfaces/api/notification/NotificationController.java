package com.restock.app.interfaces.api.notification;

import com.restock.app.domain.notification.NotificationService;
import com.restock.app.interfaces.api.common.CommonRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 재입고 알림 전송 API
     * POST /products/{productId}/notifications/re-stock
     *
     * @param productId 상품 ID
     * @return 성공 시:
     * {
     *   "resultType": "SUCCESS",
     *   "data": "재입고 알림 전송이 완료되었습니다.",
     *   "exception": null
     * }
     * 실패 시:
     * {
     *   "resultType": "FAIL",
     *   "data": {},
     *   "exception": {
     *     "code": "404",
     *     "message": "데이터를 조회할 수 없습니다."
     *   }
     * }
     */
    @PostMapping("/products/{productId}/notifications/re-stock")
    public CommonRes<String> sendRestockNotifications(@PathVariable Long productId) {
        notificationService.sendNotifications(productId);
        return CommonRes.success("재입고 알림 전송이 완료되었습니다.");
    }
}