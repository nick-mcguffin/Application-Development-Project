package com.wilma.repository;

import com.wilma.entity.positions.PositionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PositionApplicationRepository extends JpaRepository<PositionApplication, Integer> {

    Set<PositionApplication> findByViewed(boolean b);
}