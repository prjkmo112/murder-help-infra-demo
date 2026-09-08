package io.github.prjkmo112.murderhelpinfrademo.domain.item.dto;

import io.github.prjkmo112.murderhelpinfrademo.domain.item.entity.Item;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ItemResponse implements Serializable {

    private final Long id;
    private final String name;
    private final String description;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
    }
}