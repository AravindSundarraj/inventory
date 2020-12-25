package com.group.kafka.inventory.domain.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DeliveryAddressJson {
     private java.lang.String addressLine;
     private java.lang.String city;
     private java.lang.String state;
     private java.lang.String pinCode;
     private java.lang.String contactNumber;
}
