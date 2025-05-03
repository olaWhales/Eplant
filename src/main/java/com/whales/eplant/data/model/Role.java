package com.whales.eplant.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Role {

    DECORATOR,
    CATERER,
    DJ,
    MAKE_UP,
    MC,
    PHOTOGRAPHER
}
