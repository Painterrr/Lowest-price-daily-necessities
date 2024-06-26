package com.fisa.lep.product.entity;

import com.fisa.lep.common.BaseEntity;
import com.fisa.lep.mart.entity.Mart;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
    상품 이름
     */
    private String prodName;

    /*
    상품 가격
     */
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mart_id")
    private Mart martId;

}