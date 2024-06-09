package com.fisa.lep.service;


import com.fisa.lep.repository.Repository;
import com.fisa.lep.repository.RepositoryCustom;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@NoArgsConstructor
@org.springframework.stereotype.Service
public class Service {

    private RepositoryCustom rc;

    @Autowired
    public Service(RepositoryCustom rc) {
        this.rc = rc;
    }

    /**
     * @param area
     * @param product
     * @logic
     * 1. area로 해당 지역만 조회
     * 2. product 이름으로 상품 조회
     * 3. 그 안에서 최저가 조회
     * 4. 해당 최저가 판매처 이름 반환
     * @return lowest
     */
    public String read(String area, String product) {
        String lowest = String.valueOf(rc.read(area, product));

        log.info("** in service, lowest: {}", lowest);

        return lowest;
    }
}
