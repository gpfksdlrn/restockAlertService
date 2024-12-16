package com.restock.app.domain.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "Product")
public class Product {
    // 상품 정보를 저장하는 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; // 상품 고유 아이디

    @Column(name = "restock_round", nullable = false)
    private Long restockRound = 0L; // 상품 재입고 회차

    @Column(name = "stock_count", nullable = false)
    private Long stockCount = 0L; // 재고 수량
}
