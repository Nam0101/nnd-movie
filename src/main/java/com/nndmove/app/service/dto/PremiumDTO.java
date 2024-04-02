package com.nndmove.app.service.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PremiumDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long startDateId;
    private Long endDateId;
    private Long userId;
}
