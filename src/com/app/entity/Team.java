package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Team {
    private Integer experience;
    private String description;
    private Integer teamId;
}
