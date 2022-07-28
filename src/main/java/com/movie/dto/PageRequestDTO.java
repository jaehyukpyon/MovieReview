package com.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Slf4j
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;

    private int size;

    private String type;
    private String keyword;

    public PageRequestDTO() {
        log.info("PageRequestDTO's constructor...");
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPabeable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }

}
