package me.ted.code.helvetica.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Greeting {

    private String content;
}
