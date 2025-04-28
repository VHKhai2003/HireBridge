package com.vhkhai.utils;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaginationData {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;

    public static <T> PaginationData fromPage(Page<T> page) {
        return PaginationData.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
