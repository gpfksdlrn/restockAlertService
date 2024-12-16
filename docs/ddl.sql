-- PRODUCT 테이블: 상품 정보를 저장하는 테이블
CREATE TABLE `PRODUCT` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품 고유 id',
    `restock_round` bigint NOT NULL DEFAULT '0' COMMENT '상품 재입고 회차',
    `stock_count` bigint NOT NULL COMMENT '재고 수량',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='상품 정보를 저장하는 테이블';

-- PRODUCT_NOTIFICATION_HISTORY 테이블: 상품별 재입고 알림 전송 이력을 관리하는 테이블
CREATE TABLE `PRODUCT_NOTIFICATION_HISTORY` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '고유 알림 이력 id',
    `product_id` bigint NOT NULL COMMENT '상품 고유 id',
    `restock_round` bigint NOT NULL DEFAULT '0' COMMENT '상품 재입고 회차',
    `notification_status` enum('IN_PROGRESS','CANCELED_BY_SOLD_OUT','CANCELED_BY_ERROR','COMPLETED') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'IN_PROGRESS' COMMENT '재입고 알림 전송의 상태',
    `last_sent_user_id` bigint DEFAULT NULL COMMENT '마지막으로 알림 메시지를 받은 유저 아이디',
    PRIMARY KEY (`id`),
    KEY `PRODUCT_NOTIFICATION_HISTORY_PRODUCT_product_id_fk` (`product_id`),
    CONSTRAINT `PRODUCT_NOTIFICATION_HISTORY_PRODUCT_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='상품별 재입고 알림 전송 이력을 관리하는 테이블';

-- PRODUCT_USER_NOTIFICATION 테이블: 상품에 대해 알림을 설정한 유저 정보 관리
CREATE TABLE `PRODUCT_USER_NOTIFICATION` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품에 대해 알림을 설정한 유지 정보 관리',
    `product_id` bigint NOT NULL COMMENT '상품 고유 id',
    `user_id` bigint NOT NULL DEFAULT '0' COMMENT '유저 고유 id',
    `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '알림 활성화 여부',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성날짜',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '업데이트 날짜',
    PRIMARY KEY (`id`),
    KEY `PRODUCT_USER_NOTIFICATION_PRODUCT_product_id_fk` (`product_id`),
    KEY `idx_product_is_active` (`product_id`, `is_active`), -- 상품 ID와 활성화 여부 조회 최적화
    CONSTRAINT `PRODUCT_USER_NOTIFICATION_PRODUCT_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
    COMMENT='상품에 대해 알림을 설정한 유저 정보 관리 테이블';

-- PRODUCT_USER_NOTIFICATION_HISTORY 테이블: 유저별 알림 전송 이력을 관리하는 테이블
CREATE TABLE `PRODUCT_USER_NOTIFICATION_HISTORY` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '고유 유저 알림 이력 id',
    `product_id` bigint NOT NULL COMMENT '상품 고유 id',
    `user_id` bigint NOT NULL DEFAULT '0' COMMENT '유저 고유 id',
    `restock_round` bigint NOT NULL DEFAULT '0' COMMENT '상품 재입고 회차',
    `sent_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '알림 메시지 전송 날짜',
    PRIMARY KEY (`id`),
    KEY `PRODUCT_USER_NOTIFICATION_HISTORY_PRODUCT_product_id_fk` (`product_id`),
    CONSTRAINT `PRODUCT_USER_NOTIFICATION_HISTORY_PRODUCT_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `PRODUCT` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
    COMMENT='유저별 알림 전송 이력을 관리하는 테이블';