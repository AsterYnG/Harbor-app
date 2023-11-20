package com.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Team {
    private Integer experience;
    private String description;
    private Integer teamId;
}
