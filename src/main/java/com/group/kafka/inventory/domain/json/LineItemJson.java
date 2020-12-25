package com.group.kafka.inventory.domain.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class LineItemJson {
    private java.lang.String itemCode;
    private java.lang.String itemDescription;
    private java.lang.Double itemPrice;
    private java.lang.Integer itemQty;
    private java.lang.String totalValue;
}
