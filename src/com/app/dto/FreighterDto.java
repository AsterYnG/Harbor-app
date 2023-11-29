package com.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;


@Value
@Builder
public class FreighterDto {
     Integer tax;
     Integer weightCost;
     Integer sizeCost;
     Integer fragileCost;
     String freighterName;
     private double shippingPrice; // Добавлено новое поле

     // Конструктор с новым полем
     public FreighterDto(Integer tax, Integer weightCost, Integer sizeCost, Integer fragileCost, String freighterName, double shippingPrice) {
          this.tax = tax;
          this.weightCost = weightCost;
          this.sizeCost = sizeCost;
          this.fragileCost = fragileCost;
          this.freighterName = freighterName;
          this.shippingPrice = shippingPrice;
     }
}
