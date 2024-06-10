package com.fisa.lep.repository;

import com.fisa.lep.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    
}
