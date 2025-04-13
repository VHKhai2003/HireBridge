package com.vhkhai.utils;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaginationData {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
