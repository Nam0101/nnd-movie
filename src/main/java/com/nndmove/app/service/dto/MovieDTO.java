package com.nndmove.app.service.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MovieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String originName;
    private Boolean isCompleted;
    private String slug;
    private String episodeCurrent;
    private Integer episodeTotal;
    private String quality;
    private Integer year;
    private String trailerUrl;
    private String time;
    private String content;
    private Boolean isSingle;
    private String thumbUrl;
    private String posterUrl;
    private String actor;
    private String country;
    private Boolean premiumOnly;
}
