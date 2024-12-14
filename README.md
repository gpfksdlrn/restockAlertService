# 📢 재입고 알림 서비스

## 서비스 설명
재입고 알림 서비스은 상품이 재입고되었을 때, 재입고 알림을 설정한 유저들에게 알림 메시지를 전송하는 기능을 제공합니다.

 
## 비즈니스 요구 사항

1. **재입고 회차 증가**
   - 상품이 재입고될 때마다 해당 상품의 재입고 회차를 1 증가시킵니다.

2. **알림 메시지 전송**
   - 재입고 알림을 설정한 유저들에게 알림 메시지를 전송합니다.
   - 알림 메시지는 설정된 유저 순서대로 발송됩니다.
     - ProductUserNotification 테이블에 존재하는 유저는 모두 재입고 알림을 설정했다고 가정합니다.

3. **발송 중단 조건**
   - 알림 메시지를 보내던 중 재고가 모두 소진되면 알림 발송을 중단합니다.

4. **발송 상태 관리**
   - 재입고 알림의 전송 상태는 다음과 같이 관리됩니다:
     - `IN_PROGRESS`: 발송 중
     - `CANCELED_BY_SOLD_OUT`: 품절로 인한 발송 중단
     - `CANCELED_BY_ERROR`: 예외로 인한 발송 중단 (예: 서드 파티 연동 실패)
     - `COMPLETED`: 발송 완료

5. **회차별 발송 히스토리 관리**
   - 재입고 알림을 받은 유저 목록은 회차별로 저장됩니다.

6. **재시도 API 제공 (Optional)**
   - 예외로 인해 알림 메시지 발송이 실패한 경우, 수동 API를 통해 마지막으로 성공한 유저 이후부터 다시 발송할 수 있습니다.

## 기술적 요구 사항

1. **성능 제약**
   - 알림 메시지는 1초에 최대 500개의 요청을 처리할 수 있습니다.
   - 실제 메시지 전송 대신 `ProductNotificationHistory` 테이블에 데이터를 저장합니다.

2. **MySQL 최적화**
   - 조회 시 인덱스를 활용할 수 있도록 테이블을 설계합니다.

3. **테이블 설계**
   - **Product (상품)**
     
     |**컬럼 이름**|**설명**|
     |:-----|:-----:|
     | product_id | 상품 아이디 |
     | restock_round | 재입고 회차 |
     | stock_status | 재고 상태 |

    - **ProductNotificationHistory (재입고 알림 히스토리)**
 
       |**컬럼 이름**|**설명**|
       |:-----|:-----:|
       | product_id | 상품 아이디 |
       | restock_round | 재입고 회차 |
       | notification_status | 발송 상태 |
       | last_sent_user_id | 마지막 발송 유저 아이디 |

   - **ProductUserNotification (알림 설정 유저)**
   
       |**컬럼 이름**|**설명**|
       |:-----|:-----:|
       | product_id | 상품 아이디 |
       | user_id | 유저 아이디 |
       | is_active | 활성화 여부 |
       | created_at | 생성 날짜 |
       | updated_at | 수정 날짜 |
 
   - **ProductUserNotificationHistory (유저별 알림 히스토리)**
     
       |**컬럼 이름**|**설명**|
       |:-----|:-----:|
       | product_id | 상품 아이디 |
       | user_id | 유저 아이디 |
       | restock_round | 재입고 회차 |
       | sent_at | 발송 날짜 |
4. **재발송 기능**
   - 알림 발송 중 예외로 중단된 경우, manual API를 호출하여 중단된 시점 이후부터 알림 발송을 재개할 수 있습니다.
   - 마지막으로 성공적으로 알림을 받은 유저 이후부터 알림을 재발송합니다.
     - 예: 10번째 유저까지 성공 후 중단 시, 11번째 유저부터 재발송
   - 중복 발송 방지를 위해 발송 기록(ProductUserNotificationHistory)을 참조합니다.

5. **비동기 처리**
   - 모든 프로세스는 동기적으로 처리한다고 가정합니다.

6. **테스트 코드 작성 (Optional)**
   - 주요 비즈니스 로직에 대한 테스트 코드를 작성합니다.

## 고려하지 않아도 되는 사항

- 회원 가입 및 로그인 로직은 구현하지 않습니다.

## API 스펙

### 1. 재입고 알림 전송 API
- **Endpoint**: `POST /products/{productId}/notifications/re-stock`
- **RequestBody**: 없음

### 2. 재입고 알림 전송 API (수동)
- **Endpoint**: `POST /admin/products/{productId}/notifications/re-stock`
- **RequestBody**: 없음

## 기술 스택

- **Framework**: Spring Boot 3.x
- **Database**: MySQL 8.0 이상
- **기타**: 필요에 따라 추가적인 저장소나 기술 스택을 사용할 수 있습니다.

---
