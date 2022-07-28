package com.movie.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        this.totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 add
        this.size = pageable.getPageSize(); // 한 페이지 당 몇 개의 글의 개수

        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;

        this.start = tempEnd - 9;

        // boolean
        this.prev = this.start > 1;

        this.end = this.totalPage > tempEnd ? tempEnd : this.totalPage;

        // boolean
        this.next = this.totalPage > tempEnd;

        // IntStream.rangeClosed(int startInclusive, int endInclusive)
        this.pageList = IntStream.rangeClosed(this.start, this.end).boxed().collect(Collectors.toList());
    }

}
