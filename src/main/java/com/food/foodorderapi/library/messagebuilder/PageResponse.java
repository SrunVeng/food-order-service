package com.food.foodorderapi.library.messagebuilder;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;

@Data
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private String sort;

    public static <X> PageResponse<X> from(Page<X> page) {
        String sortStr = page.getSort().isSorted()
                ? StreamSupport.stream(page.getSort().spliterator(), false)
                .map(o -> o.getProperty() + ":" + o.getDirection().name())
                .collect(Collectors.joining(","))
                : "unsorted";
        return PageResponse.<X>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .sort(sortStr)
                .build();
    }
}
