package com.movie.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;
    
    private int totalPage; // 총 페이지 번호
    
    private int page; // current page
    
    private int size; // 목록 사이즈
    
    private int start;
    private int end; // 시작 및 끝 페이지 번호
    
    private boolean prev;
    private boolean next;
    
    private List<Integer> pageList; // 페이지 번호 목록

    // constructor
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

}
