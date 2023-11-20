package com.app.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public final class TeamMember {
    private Integer memberId;
    private String fullName;
    private String position;
    private LocalDate birthDate;
    private String citizenship;
    private Team team;
}
