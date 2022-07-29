package com.movie.repository;

import com.movie.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying // JPQL을 이용하여 update, delete를 실행하기 위해서는 @Modifying annotation을 같이 추가 필요
    @Query("DELETE FROM Reply r " +
           "WHERE r.board.bno = :bno")
    public void deleteByBno(@Param("bno") Long bno);

}
