package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Employment {
    private String employmentSerialNumber;
    private String previousJob;
    private Integer experience;
}

