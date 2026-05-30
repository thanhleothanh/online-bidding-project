package com.ghtk.onlinebiddingproject.domain.model.auction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    private final Integer id;
    private final String name;

}
