-- Product 테이블: 상품 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS Product (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '상품 고유 아이디',
    restock_round BIGINT DEFAULT 0 NOT NULL COMMENT '상품 재입고 회차',
    stock_count BIGINT DEFAULT 0 NOT NULL COMMENT '재고 수량'
) COMMENT = '상품 정보를 저장하는 테이블';

-- ProductNotificationHistory 테이블: 상품별 재입고 알림 전송 이력을 관리하는 테이블
CREATE TABLE IF NOT EXISTS ProductNotificationHistory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '고유 알림 이력 ID',
    product_id BIGINT NOT NULL COMMENT '상품 아이디',
    restock_round BIGINT NOT NULL COMMENT '상품의 재입고 회차',
    notification_status ENUM('IN_PROGRESS', 'CANCELED_BY_SOLD_OUT', 'CANCELED_BY_ERROR', 'COMPLETED') NOT NULL COMMENT '재입고 알림 전송 상태',
    last_sent_user_id BIGINT DEFAULT NULL COMMENT '마지막으로 알림 메시지를 받은 유저 아이디',
    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE,
    UNIQUE KEY unique_product_round_status_user (product_id, restock_round, notification_status, last_sent_user_id),
    INDEX idx_product_restock (product_id, restock_round) -- 조회 최적화를 위한 인덱스
) COMMENT = '상품별 재입고 알림 전송 이력을 관리하는 테이블';

-- ProductUserNotification 테이블: 상품에 대해 알림을 설정한 유저 정보 관리
CREATE TABLE IF NOT EXISTS ProductUserNotification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '고유 알림 ID',
    product_id BIGINT NOT NULL COMMENT '상품 아이디',
    user_id BIGINT NOT NULL COMMENT '유저 아이디',
    is_active BOOLEAN DEFAULT TRUE NOT NULL COMMENT '알림 활성화 여부 (TRUE: 활성화, FALSE: 비활성화)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 날짜',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 날짜 (알림 활성화/비활성화 업데이트 시 갱신)',
    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE,
    INDEX idx_product_user (product_id, user_id)
) COMMENT = '상품에 대해 알림을 설정한 유저 정보 관리';

-- ProductUserNotificationHistory 테이블: 유저별 알림 전송 이력을 관리하는 테이블
CREATE TABLE IF NOT EXISTS ProductUserNotificationHistory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '고유 알림 전송 이력 ID',
    product_id BIGINT NOT NULL COMMENT '상품 아이디',
    user_id BIGINT NOT NULL COMMENT '유저 아이디',
    restock_round BIGINT NOT NULL COMMENT '상품 재입고 회차',
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '알림 메시지 전송 날짜',
    FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_notification (product_id, user_id, restock_round),
    INDEX idx_user_notification_history (product_id, user_id, restock_round),
    INDEX idx_user_id (user_id)
) COMMENT = '유저별 재입고 알림 전송 이력 관리';
