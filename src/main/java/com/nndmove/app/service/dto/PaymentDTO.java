package com.nndmove.app.service.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long paymentTime;
    private Long paymentPrice;
    private String paymentMethod;
    private Long userId;
}
