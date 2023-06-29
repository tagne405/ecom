package com.ecommerce.ecommerce2.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesombPayRequestData {
    private String payer;
    private String amount;
    private Boolean fees;
    private String service;
    private String country;
    private String currency;
}
