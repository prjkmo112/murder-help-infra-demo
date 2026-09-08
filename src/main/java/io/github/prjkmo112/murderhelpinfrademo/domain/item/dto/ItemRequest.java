package io.github.prjkmo112.murderhelpinfrademo.domain.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ItemRequest {

    @NotBlank
    private String name;

    private String description;
}