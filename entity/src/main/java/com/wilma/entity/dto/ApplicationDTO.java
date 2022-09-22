package com.wilma.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationDTO {
    private int positionId;
    private List<Integer> fileIds;
    private String message;
}
