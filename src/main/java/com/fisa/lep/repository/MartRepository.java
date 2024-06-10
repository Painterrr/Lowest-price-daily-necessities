package com.fisa.lep.repository;

import com.fisa.lep.mart.entity.Mart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MartRepository extends JpaRepository<Mart, Long> {
    Optional<Mart> findByMartName(String martName);
}