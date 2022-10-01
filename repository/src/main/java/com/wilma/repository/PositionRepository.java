package com.wilma.repository;

import com.wilma.entity.positions.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    List<Position> findByApproved(boolean approved);
}