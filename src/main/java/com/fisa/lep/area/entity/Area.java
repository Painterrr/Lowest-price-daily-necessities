package com.fisa.lep.area.entity;

import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "area")
@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 지역 전체 이름: 서울특별시 송파구 가락1동
     */
    @Column(name = "full_addr")
    private String fullAddr;

    /**
     * 행정동 코드
     */
    @Column(name = "h_code")
    private String hCode;
}