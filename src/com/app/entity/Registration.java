package com.app.entity;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public final class Registration {
    private Integer regId;
    private String house;
    private Integer flat;
    private String region;
    private String city;
    private String street;
}