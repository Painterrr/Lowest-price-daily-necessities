package com.fisa.lep.mart.entity;

import com.fisa.lep.area.entity.Area;
import com.fisa.lep.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "mart")
@Entity
public class Mart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
    판매업소명
     */
    private String name;

    /*
    소속 브랜드
     */
    private String brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area areaId;

}