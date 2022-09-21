package com.wilma.service.positions;

import com.wilma.entity.positions.Position;
import com.wilma.repository.PositionRepository;
import com.wilma.service.forum.CrudOpsImpl;
import org.springframework.stereotype.Service;

@Service
public class PositionService extends CrudOpsImpl<Position, Integer, PositionRepository> {

}