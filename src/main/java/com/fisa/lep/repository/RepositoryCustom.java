package com.fisa.lep.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.fisa.lep.area.entity.QArea;
import com.fisa.lep.mart.entity.QMart;
import com.fisa.lep.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 1. area로 해당 지역만 조회
     * 2. product 이름으로 상품 조회
     * 3. 그 안에서 최저가 조회
     * 4. 해당 최저가 판매처 이름 반환
     */
    public Optional<String> read(String area, String product) {
        QArea qArea = QArea.area;
        QMart qMart = QMart.mart;
        QProduct qProduct = QProduct.product;

        // Subquery to filter marts by area
        List<Long> areaIds = queryFactory
                .select(qArea.id)
                .from(qArea)
                .where(qArea.fullAddr.contains(area))
                .fetch();

        // Main query to find the lowest price products in those marts
        List<Tuple> result = queryFactory
                .select(qMart.martName, qProduct.prodName, qProduct.price)
                .from(qProduct)
                .join(qMart).on(qProduct.martId.id.eq(qMart.id))
                .where(qMart.areaId.id.in(areaIds)
                        .and(qProduct.prodName.eq(product)))
                .orderBy(qProduct.price.asc())
                .limit(3)
                .fetch();

        // Process the result to get the desired format
        StringBuilder resultString = new StringBuilder();
        for (Tuple tuple : result) {
            String martName = tuple.get(qMart.martName);
            String prodName = tuple.get(qProduct.prodName);
            Double price = tuple.get(qProduct.price).doubleValue();
            resultString.append(String.format("Mart: %s, Product: %s, Price: %.2f\n", martName, prodName, price));
        }

        return Optional.ofNullable(resultString.toString());
    }
}
