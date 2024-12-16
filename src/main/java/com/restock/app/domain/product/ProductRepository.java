package com.restock.app.domain.product;

public interface ProductRepository {
    Product findById(Long productId);
}
