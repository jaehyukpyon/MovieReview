package com.movie.repository;

import com.movie.entity.Memo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    //@Test
    public void testClass() {
        log.info("memoRepository.getClass().getName() : {}", memoRepository.getClass().getName());
    }

    //@Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample : " + i).build();
            memoRepository.save(memo);
        });
    }

    //@Test
    public void testPageDefault() {
        // 1 페이지 10개
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        log.info(result.toString());
        log.info("--------------------------------------------------");

        log.info("Total Pages : {}", result.getTotalPages());
        log.info("Total Elements : {}", result.getTotalElements());
        log.info("Current Page Number(starts from 0) : {}", result.getNumber());
        log.info("Elements per Pages : {}", result.getSize());
        log.info("has next page : {}", result.hasNext());
        log.info("is first page : {}", result.isFirst());
    }

    //@Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetweenOrderByMnoDesc(10L, 50L, pageable);
        log.info("class type : " + result.getContent().get(0).getClass()); // class type : class com.movie.entity.Memo
        // System.out.println(result.getContent().get(0).getClass()); // class com.movie.entity.Memo
        result.get().forEach(memo -> log.info(memo.toString()));
    }

//    @Transactional
//    @Commit
//    @Test
    public void testDeleteQueryMethods() {
        ;
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

}