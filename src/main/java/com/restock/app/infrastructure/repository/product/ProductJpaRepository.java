package com.restock.app.infrastructure.repository.product;

import com.restock.app.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
