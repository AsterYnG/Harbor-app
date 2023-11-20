package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Position {
    private String position;
    private Integer salary;
    private String description;
}