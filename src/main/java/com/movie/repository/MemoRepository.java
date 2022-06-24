package com.movie.repository;

import com.movie.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    // Memo의 mno 값이 70 ~ 80 및 mno desc order
    public abstract List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    // parameter에 Pageable이 포함 되어 있다면, 무조건 Page로 return 받아야 함
    public abstract Page<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to, Pageable pageable);

    public abstract void deleteMemoByMnoLessThan(Long mno);
}
