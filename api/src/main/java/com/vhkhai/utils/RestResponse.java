package com.vhkhai.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {
    private Integer status;
    private String message;
    private PaginationData paginationData;
    private T data;

    public RestResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public RestResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public RestResponse withData(T data) {
        this.data = data;
        return this;
    }

    public RestResponse withPaginationData(PaginationData paginationData) {
        this.paginationData = paginationData;
        return this;
    }


    public ResponseEntity buildHttpResponseEntity () {
        return new ResponseEntity<>(this, HttpStatus.valueOf(status));
    }

}