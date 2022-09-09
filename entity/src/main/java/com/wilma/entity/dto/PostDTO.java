package com.wilma.entity.dto;

import lombok.Data;

@Data
public class PostDTO {
    private String title;
    private String body;
    private String category;
    private String[] tags;
}
