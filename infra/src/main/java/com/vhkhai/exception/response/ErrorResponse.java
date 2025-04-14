package com.vhkhai.exception.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    private int status;
    private String message;
    private Date timestamp;
}
