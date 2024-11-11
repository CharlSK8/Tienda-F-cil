package com.tienda.facil.core.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private transient T response;
    private String message;
    private int code;
}