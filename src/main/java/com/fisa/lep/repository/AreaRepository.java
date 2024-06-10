package com.fisa.lep.repository;

import com.fisa.lep.area.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    Area findByhCode(String hCode);
}