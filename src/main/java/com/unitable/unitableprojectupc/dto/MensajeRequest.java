package com.unitable.unitableprojectupc.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class MensajeRequest {
    @NotBlank
    private String mensaje;
    @NotNull
    private Long chat_id;
}
