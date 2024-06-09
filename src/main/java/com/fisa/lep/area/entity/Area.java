package com.fisa.lep.area.entity;

import com.fisa.lep.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "area")
@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
    지역 전체 이름: 서울특별시 송파구 가락1동
     */
    private String fullAddr;

}