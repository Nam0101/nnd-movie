package com.nndmove.app.service.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MovieResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer part;
    private String linkEmbed;
    private String linkM3u8;
    private Long movieId;
}
