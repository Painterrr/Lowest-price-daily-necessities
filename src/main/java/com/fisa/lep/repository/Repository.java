package com.fisa.lep.repository;

import com.fisa.lep.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Product, Integer> {
    
}
